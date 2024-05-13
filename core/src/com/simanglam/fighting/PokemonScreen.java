package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.badlogic.gdx.Input;


public class PokemonScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Texture texture;
    //private viewport;
    Texture map;
    Texture pokemon;

    public PokemonScreen(final Main game){
    map = new Texture("bosswar.png");
    pokemon = new Texture("enemies/base/image/idle-0.png");
    batch = new SpriteBatch();
    }

    public void create() {
    }
    

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(map, 0, 0);
        batch.draw(pokemon,50,120);
        batch.draw(pokemon,600,120);
        batch.end();
    }
    
    private void update(float delta) {
        // 在這裡添加遊戲邏輯的更新代碼，例如移動角色、處理碰撞等。
    }
    
    public void handleInput() {
    }
    
    public void resize(int x, int y){

    }

    @Override
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}