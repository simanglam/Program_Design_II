package com.simanglam.fighting.bosswar;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simanglam.util.Const;

public class BossWarWorld extends InputAdapter {
    ArrayList<BossWarActor> pokemonArray;
    ArrayList<BossWarActor> enemyArray;
    ArrayList<EnemySpawnInfo> enemySpawnInfos;
    ArrayList<AttackInfo> pendingAttack;
    BossWarActor enemyTower;
    BossWarActor playerTower;
    Texture map;
    Viewport viewport;
    OrthographicCamera camera;
    Vector3 lastTouchDown;
    int money;
    int maxEnemy;
    float spawnAccu;

    public BossWarWorld(){
        pokemonArray = new ArrayList<>();
        enemyArray = new ArrayList<>();
        pendingAttack = new ArrayList<>();
        enemySpawnInfos = new ArrayList<>();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false);
        this.viewport = new FitViewport(Const.maxViewportWidth / 2f, Const.maxViewportHeight / 2f, camera);
        this.viewport.getCamera().position.set(Const.maxViewportWidth / 4f, Const.maxViewportHeight / 4f, 0);
        this.viewport.apply();
        this.enemyTower = new BossWarActor("enemies/base/", true);
        enemyTower.setPosition(0, 120);
        this.playerTower = new BossWarActor("enemies/base/", false);
        lastTouchDown = new Vector3(0, 0, 0);
        this.money = 0;
        Json json = new Json();
        json.setElementType(BossWarInfo.class, "onstage", SpawnInfo.class);
        json.setElementType(BossWarInfo.class, "enemies", SpawnInfo.class);
        BossWarInfo bs = json.fromJson(BossWarInfo.class, Gdx.files.internal("bosswar/ex1.json"));
        for (SpawnInfo info: bs.onstage){
            for (int i = 0; i < info.spawnCoolDown; i++)
                enemyArray.add(new BossWarActor("enemies/" + info.name + "/", true));
        }
        maxEnemy = 0;
        spawnAccu = 1.0f;
        for (SpawnInfo info: bs.enemies){
            enemySpawnInfos.add(new EnemySpawnInfo(new BossWarActor("enemies/" + info.name + "/", true), info.spawnCoolDown));
            maxEnemy = (maxEnemy < info.spawnCoolDown) ? info.spawnCoolDown : maxEnemy;
        }
        map = new Texture(bs.image);
    }

    public void centerCamera(){
        if(camera.position.x + this.viewport.getWorldWidth() / 2 >= map.getWidth())
            camera.position.x = map.getWidth() - this.viewport.getWorldWidth() / 2;
        else if(camera.position.x - this.viewport.getWorldWidth() / 2 <= 0)
            camera.position.x = this.viewport.getWorldWidth() / 2;
    }

    public int getMoney(){
        return this.money;
    }

    @Override
    public boolean scrolled(float x, float y){
        if (viewport.getWorldWidth() + 10 * y <= map.getWidth() && viewport.getWorldHeight() + 10 * y <= map.getHeight() && viewport.getWorldWidth() + 10 * y > Const.maxViewportWidth / 2f&& viewport.getWorldHeight() + 10 * y > Const.maxViewportHeight / 2f){
            viewport.setWorldSize(viewport.getWorldWidth() + 10 * y, viewport.getWorldHeight() + 10 * y);
            if (this.camera.position.y - viewport.getWorldHeight() / 2 != 0)
                this.camera.position.y -= this.camera.position.y - viewport.getWorldHeight() / 2;
        }
        viewport.apply();
        viewport.getCamera().update();
        centerCamera();
        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        lastTouchDown.set(x, 0, 0);
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button){
        lastTouchDown.set(0, 0, 0);
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer){
        camera.position.add(lastTouchDown.sub(x, 0, 0));
        centerCamera();
        lastTouchDown.set(x, 0, 0);
        return true;
    }

    public void resize(int x, int y){
        this.viewport.setScreenSize(x, y);
        if (this.camera.position.y - viewport.getWorldHeight() / 2 != 0)
            this.camera.position.y -= this.camera.position.y - viewport.getWorldHeight() / 2;
        this.viewport.apply();
        lastTouchDown.x = 0;
    }

    public void update(float delta, SpriteBatch batch){
        this.money += Math.max((int)delta, 1);
        spawnAccu += delta;
        for (EnemySpawnInfo info: enemySpawnInfos){
            if ((int)spawnAccu != 0 && (int)spawnAccu % info.spawnCoolDown == 0){
                enemyArray.add(new BossWarActor(info.enemy));
            }
        }
        if (spawnAccu >= maxEnemy){
            spawnAccu -= maxEnemy;
            System.out.println(spawnAccu);
        }

        this.viewport.getCamera().update();
        batch.setProjectionMatrix(this.viewport.getCamera().combined);
        batch.begin();
        batch.draw(map, 0, 0);
        enemyTower.draw(batch);
        playerTower.draw(batch);
        for (BossWarActor actor: pokemonArray){
            actor.update(delta, detectEnemy(false, actor.getVisionRange()));
            actor.draw(batch);
            if(actor.readyToAttack())
                pendingAttack.add(actor.generateAttackInfo());
        }
        for (BossWarActor actor: enemyArray){
            actor.update(delta, detectEnemy(true, actor.getVisionRange()));
            actor.draw(batch);
            if(actor.readyToAttack())
                pendingAttack.add(actor.generateAttackInfo());
        }
        for (AttackInfo attackInfo: pendingAttack){
            for (BossWarActor actor : (attackInfo.from.equals("enemy")) ? pokemonArray : enemyArray)
                actor.beingAttack(attackInfo);
        }
        for (int i = 0; i < pokemonArray.size(); i++){
                BossWarActor actor = pokemonArray.get(i);
                if (actor.healtPoint <= 0){
                    pokemonArray.remove(actor);
                }
        }
        for (int i = 0; i < enemyArray.size(); i++){
                BossWarActor actor = enemyArray.get(i);
                if (actor.healtPoint <= 0){
                    boolean b = enemyArray.remove(actor);
                }
        }
        pendingAttack.clear();
        batch.end();
    }

    public boolean detectEnemy(boolean enemy, Rectangle rectangle){
        if (Intersector.overlaps((enemy) ? playerTower.position :enemyTower.position, rectangle)){
            return true;
        }
        for (BossWarActor actor : (enemy) ? pokemonArray : enemyArray){
            if (Intersector.overlaps(rectangle, actor.position))
                return true;
        }
        return false;
    }
}

class BossWarInfo {
    public String image;
    public ArrayList<SpawnInfo> onstage;
    public ArrayList<SpawnInfo> enemies;
}

class SpawnInfo {
    public String name;
    int spawnCoolDown;
}

class EnemySpawnInfo {
    BossWarActor enemy;
    int spawnCoolDown;

    public EnemySpawnInfo(BossWarActor proto, int coolDown) {
        this.enemy = proto;
        spawnCoolDown = coolDown;
    }
    
}