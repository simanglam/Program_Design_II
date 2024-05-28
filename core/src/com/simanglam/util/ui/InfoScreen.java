package com.simanglam.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;

public class InfoScreen extends AbstractScreen {
    Main game;
    Stage stage;
    InputMultiplexer inputMultiplexer;

    public InfoScreen(final Main game){
        this.game = game;
        this.stage = new Stage(new ExtendViewport(Const.maxViewportWidth, Const.maxViewportHeight));
        this.inputMultiplexer = new InputMultiplexer(this, stage);
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Window window = new Window("Info", skin);
        window.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        window.add(new Label("Name: ", skin)).expandX().expandY().pad(1, 1, 1, 1).top().left();
        this.stage.addActor(window);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getViewport().apply(true);
    }

    @Override
    public void handleInput(){
        
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keyCode){
        game.setScreen(game.getGameScreen());
        return true;
    }

}