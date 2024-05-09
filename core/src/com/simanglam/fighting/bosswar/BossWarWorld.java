package com.simanglam.fighting.bosswar;

import java.util.ArrayList;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simanglam.util.Const;

public class BossWarWorld extends InputAdapter {
    ArrayList<BossWarActor> pokemonArray;
    ArrayList<BossWarActor> enemyArray;
    ArrayList<AttackInfo> pendingAttack;
    BossWarActor enemyTower;
    BossWarActor playserTower;
    Texture map;
    Viewport viewport;
    OrthographicCamera camera;
    Vector3 lastTouchDown;

    public BossWarWorld(){
        pokemonArray = new ArrayList<BossWarActor>();
        enemyArray = new ArrayList<BossWarActor>();
        pendingAttack = new ArrayList<AttackInfo>();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false);
        this.viewport = new FitViewport(Const.maxViewportWidth / 2, Const.maxViewportHeight / 2, camera);
        this.viewport.getCamera().position.set(Const.maxViewportWidth / 4, Const.maxViewportHeight / 4, 0);
        this.viewport.apply();
        this.enemyTower = new BossWarActor("enemies/base/", true);
        enemyTower.setPosition(0, 60);
        this.playserTower = new BossWarActor("enemies/base/", false);
        pokemonArray.add(new BossWarActor("enemies/base/", false));
        enemyArray.add(new BossWarActor("enemies/base/", true));
        enemyArray.get(0).setPosition(0, 60);
        map = new Texture("bosswar.png");
        lastTouchDown = new Vector3(0, 0, 0);
    }

    public void centerCamera(){
        if(camera.position.x + this.viewport.getWorldWidth() / 2 >= map.getWidth())
            camera.position.x = map.getWidth() - this.viewport.getWorldWidth() / 2;
        else if(camera.position.x - this.viewport.getWorldWidth() / 2 <= 0)
            camera.position.x = this.viewport.getWorldWidth() / 2;
    }

    public boolean keyDown(int keycode){
        pokemonArray.get(0).setPosition(Const.maxViewportWidth / 2, pokemonArray.get(0).position.y);

        return true;
    }

    public boolean scrolled(float x, float y){
        if (viewport.getWorldWidth() + 10 * y <= map.getWidth() && viewport.getWorldHeight() + 10 * y <= map.getHeight() && viewport.getWorldWidth() + 10 * y > Const.maxViewportWidth / 2 && viewport.getWorldHeight() + 10 * y > Const.maxViewportHeight / 2){
            viewport.setWorldSize(viewport.getWorldWidth() + 10 * y, viewport.getWorldHeight() + 10 * y);
            viewport.apply();
            if (this.camera.position.y - viewport.getWorldHeight() / 2 != 0)
                this.camera.position.y -= this.camera.position.y - viewport.getWorldHeight() / 2;
        }
        centerCamera();
        return true;
    }

    public boolean touchDown(int x, int y, int pointer, int button){
        lastTouchDown.set(x, 0, 0);
        return true;
    }

    public boolean touchUp(int x, int y, int pointer, int button){
        lastTouchDown.set(0, 0, 0);
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer){
        camera.position.add(lastTouchDown.sub(x, 0, 0));
        centerCamera();
        lastTouchDown.set(x, 0, 0);
        return true;
    }

    public void resize(int x, int y){
        this.viewport.setScreenSize(x, y);;
        this.viewport.apply();
        centerCamera();
        if (this.camera.position.y - viewport.getWorldHeight() / 2 != 0)
            this.camera.position.y -= this.camera.position.y - viewport.getWorldHeight() / 2;
        lastTouchDown.x = 0;
    }

    public void update(float delta, SpriteBatch batch){
        this.viewport.getCamera().update();
        batch.setProjectionMatrix(this.viewport.getCamera().combined);
        batch.begin();
        batch.draw(map, 0, 0);
        enemyTower.draw(batch);
        playserTower.draw(batch);
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
            System.out.println(attackInfo);
            for (BossWarActor actor : (attackInfo.from.equals("enemy")) ? pokemonArray : enemyArray)
                actor.beingAttack(attackInfo);
        }
        for (int i = 0; i < pokemonArray.size(); i++){
            try {
                BossWarActor actor = pokemonArray.get(i);
                if (actor.healtPoint <= 0)
                    pokemonArray.remove(actor);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        for (int i = 0; i < enemyArray.size(); i++){
            try {
                BossWarActor actor = enemyArray.get(i);
                if (actor.healtPoint <= 0)
                    enemyArray.remove(actor);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        pendingAttack.clear();
        batch.end();
    }

    public boolean detectEnemy(boolean enemy, Rectangle rectangle){
        if (Intersector.overlaps((enemy) ? playserTower.position :enemyTower.position, rectangle)){
            return true;
        }
        for (BossWarActor actor : (enemy) ? pokemonArray : enemyArray){
            if (Intersector.overlaps(rectangle, actor.position))
                return true;
        }
        return false;
    }
}
