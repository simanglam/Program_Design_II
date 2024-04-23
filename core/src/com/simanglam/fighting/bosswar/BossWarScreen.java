package com.simanglam.fighting.bosswar;

import com.simanglam.Main;
import com.simanglam.util.Const;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BossWarScreen implements Screen {
    Main game;
    BossWarWorld world;
    Stage Stage;
    Camera camera;

    public BossWarScreen(final Main game){
        this.game = game;
        this.world = new BossWarWorld();
        this.Stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera()));
        this.camera = new OrthographicCamera();
    }

    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 0);
        this.world.update(delta, game.getSpriteBatch());
    };

    public void resize(int x, int y){
        this.Stage.getViewport().update(x, y);
    }

    public void pause(){};
    public void show(){};
    public void hide(){};
    public void resume(){};
    public void dispose(){};
}
