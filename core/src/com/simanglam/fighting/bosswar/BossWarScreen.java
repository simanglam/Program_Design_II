package com.simanglam.fighting.bosswar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;

public class BossWarScreen extends AbstractScreen {
    Main game;
    BossWarWorld world;
    Stage stage;
    Camera camera;

    public BossWarScreen(final Main game){
        this.game = game;
        this.world = new BossWarWorld();
        this.stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera()));
        this.camera = new OrthographicCamera();
        Button b1 = new Button(new Skin(Gdx.files.internal("data/uiskin.json")));
        ImageButton i1 = new ImageButton(new TextureRegionDrawable(new Texture("enemies/base/image/idle-0.png")));
        ImageButton i2 = new ImageButton(new TextureRegionDrawable(new Texture("enemies/base/image/idle-0.png")));
        ImageButton i3 = new ImageButton(new TextureRegionDrawable(new Texture("enemies/base/image/idle-0.png")));
        ImageButton i4 = new ImageButton(new TextureRegionDrawable(new Texture("enemies/base/image/idle-0.png")));
        b1.add(i1);
        b1.setColor(255, 255, 255, 255);
        b1.setSize(100, 100);
        i1.setSize(100, 100);
        i1.setColor(255, 255, 255, 255);
        this.stage.addActor(b1);
    }

    public void handleInput(){
        Gdx.input.setInputProcessor(world);
    }

    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 0);
        this.world.update(delta, game.getSpriteBatch());
        this.stage.act(delta);
        this.stage.getViewport().apply();
        this.stage.draw();
    }

    public void resize(int x, int y){
        this.stage.getViewport().update(x, y);
        world.resize(x, y);
    }

    public void pause(){};
    public void show(){};
    public void hide(){};
    public void resume(){};
    public void dispose(){};
}