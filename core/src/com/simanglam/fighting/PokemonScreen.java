package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.badlogic.gdx.Input;


public class PokemonScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Texture texture;

    public PokemonScreen(final Main game){
    }

    public void create() {
        batch = new SpriteBatch();
    }
    

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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