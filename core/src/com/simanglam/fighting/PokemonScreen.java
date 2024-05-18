package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;

public class PokemonScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Texture texture;

    public PokemonScreen(final Main game){}

    public void create() {
        batch = new SpriteBatch();
    }

    public void handleInput(){

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
    }

    public void resize(int x, int y){

    }

    @Override
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}