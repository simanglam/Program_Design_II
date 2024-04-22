package com.simanglam.fighting;

import com.simanglam.Main;
import com.simanglam.util.Const;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BossWarScreen implements Screen {
    Main game;
    Stage stage;

    public BossWarScreen(final Main game){
        this.game = game;
        this.stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera()));
    }

    public void render(float deltat){

    };

    public void resize(int x, int y){

    }

    public void pause(){};
    public void show(){};
    public void hide(){};
    public void resume(){};
    public void dispose(){};
}
