package com.simanglam.fighting.bosswar;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simanglam.util.Const;

public class BossWarWorld extends InputAdapter {
    ArrayList<BossWarActor> pokemonArray;
    Viewport viewport;
    OrthographicCamera camera;
    public BossWarWorld(){
        pokemonArray = new ArrayList<BossWarActor>();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false);
        this.viewport = new FitViewport(Const.maxViewportWidth / 2, Const.maxViewportHeight / 2, camera);
        this.viewport.getCamera().position.set(Const.maxViewportWidth / 4, Const.maxViewportHeight / 4, 0);
        this.viewport.apply();
        pokemonArray.add(new BossWarActor("Cute RPG World/Enemies/Enemy_01_01.png", false));
        Gdx.input.setInputProcessor(this);
    }
    public boolean keyDown(int keycode){pokemonArray.get(0).position.set(0, pokemonArray.get(0).position.y); return true;}

    public void update(float delta, SpriteBatch batch){
        this.viewport.getCamera().update();
        batch.setProjectionMatrix(this.viewport.getCamera().combined);
        batch.begin();
        for (BossWarActor actor: pokemonArray){
            actor.update(delta, false);
            System.out.println(actor.position);
            actor.draw(batch);
        }
        batch.end();
    }
}
