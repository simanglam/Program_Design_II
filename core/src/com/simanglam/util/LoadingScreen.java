package com.simanglam.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;

public class LoadingScreen extends AbstractScreen{
    Main game;
    AssetsManagerWrapper assetsManagerWrapper;
    Stage stage;
    ProgressBar progressBar;

    public LoadingScreen(final Main game){
        this.game = game;
        assetsManagerWrapper = AssetsManagerWrapper.getAssetsManagerWrapper();
        FileHandle files = Gdx.files.local("");
        Array<String> fileHandles = new Array<>();
        this.readFile(files, fileHandles);
        assetsManagerWrapper.assetManager.load("data/uiskin.json", Skin.class);
        assetsManagerWrapper.assetManager.finishLoading();
        for (String fh: fileHandles){
            if (fh.endsWith(".png") || fh.endsWith(".jpg"))
            assetsManagerWrapper.assetManager.load(fh, Texture.class);
            else if (fh.endsWith(".fnt"))
            assetsManagerWrapper.assetManager.load(fh, BitmapFont.class);
        }
        stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight));
        progressBar = new ProgressBar(0f, 1f, 0.1f, false, assetsManagerWrapper.assetManager.get("data/uiskin.json", Skin.class));
        progressBar.setPosition(Const.maxViewportWidth / 2, Const.maxViewportHeight / 2);
        progressBar.setAnimateDuration(0.1f);
        stage.addActor(progressBar);
    }

    final public void readFile(FileHandle begin, Array<String> handles){
        FileHandle[] newHandles = begin.list();
        for (FileHandle f : newHandles){
            if (f.isDirectory()){
                readFile(f, handles);
            }
            else{
                handles.add(f.path());
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        if (assetsManagerWrapper.assetManager.update(17)){
            game.setScreen(game.getGameScreen());
        }
        progressBar.setValue(assetsManagerWrapper.assetManager.getProgress());
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getViewport().apply();
    }

    @Override
    public void handleInput() {
    }
    
}
