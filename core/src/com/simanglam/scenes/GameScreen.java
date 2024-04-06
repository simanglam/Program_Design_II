package com.simanglam.scenes;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.simanglam.Const;
import com.simanglam.Dialog;
import com.simanglam.Main;
import com.simanglam.World;

public class GameScreen extends InputAdapter implements Screen{
    Main game;
    Stage stage;
    World world;
    InputMultiplexer inputMultiplexer;
    Logger logger;
    Dialog dialog;

    public GameScreen(final Main game){
        this.game = game;
        this.world = new World();
        this.stage = new Stage(new ExtendViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera()));
        this.stage.getCamera().position.set(0, 480, 0);
        this.logger = Logger.getLogger("Main");
        this.logger.setLevel(Level.ALL);
        this.inputMultiplexer = new InputMultiplexer();
        this.inputMultiplexer.addProcessor(this.stage);
        this.inputMultiplexer.addProcessor(this.world.player);
        this.inputMultiplexer.addProcessor(this);
        this.dialog = new Dialog(this.stage);
        this.handleInput();
    }

    public void handleInput(){
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    public void render(float deltaT){
        ScreenUtils.clear(0, 0, 0, 0);
        SpriteBatch batch = game.getSpriteBatch();
        this.world.update(deltaT);

        batch.setProjectionMatrix(this.world.camera.combined);
        this.stage.act();
        this.world.render(batch);
        stage.getViewport().apply();
        this.stage.draw();
    }

    public void show(){
        
    }

    public void hide(){
        
    }

    public void pause(){
        
    }

    public void resume(){
        
    }

    public void resize(int x, int y){
        this.world.resize(x, y);
        stage.getViewport().update(x, y);
        this.dialog.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight() / 5);
    }

    public void dispose(){

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.Z){
            Rectangle iRectangle = this.world.player.creatInvestgateRectangle();
            RectangleMapObject collMapObject = world.getCollideObject(iRectangle);
            if (collMapObject != null && collMapObject.getProperties().get("description") != null){
                dialog.setDescription((String)collMapObject.getProperties().get("description"));
                stage.addActor(dialog);
            }
            return true;
        }
        else if(keycode == Keys.ESCAPE){
            this.world.player.freeze();
            game.setScreen(new InfoScreen(this, game));
        }
        return false;
    }
}

