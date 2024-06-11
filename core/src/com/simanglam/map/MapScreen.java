
package com.simanglam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.map.ui.Dialog;
import com.simanglam.map.ui.PuzzleDialog;
import com.simanglam.store.StoreScreen;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;
import com.simanglam.util.JsonLoaders;
import com.simanglam.util.ui.PackageScreen;

public class MapScreen extends AbstractScreen{
    Main game;
    Stage stage;
    World world;
    InputMultiplexer inputMultiplexer;
    Dialog dialog;
    GameStatus gameStatus;

    public MapScreen(final Main game){
        this.game = game;
        this.world = new World();
        this.stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera()));
        this.stage.getCamera().position.set(0, 480, 0);
        this.inputMultiplexer = new InputMultiplexer();
        this.inputMultiplexer.addProcessor(this.stage);
        this.inputMultiplexer.addProcessor(this.world.player);
        this.inputMultiplexer.addProcessor(this);
        this.dialog = new Dialog();
        gameStatus = GameStatus.getGameStatus();
    }

    @Override
    public void handleInput(){
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    @Override
    public void render(float deltaT){
        ScreenUtils.clear(0, 0, 0, 0);
        SpriteBatch batch = game.getSpriteBatch();
        
        this.world.update(deltaT, this);
        batch.setProjectionMatrix(this.world.camera.combined);
        this.stage.act();
        this.world.render(batch);
        stage.getViewport().apply();
        this.stage.draw();
    }

    public void addDialog(String description){
        dialog.setDescription(description);
        stage.addActor(dialog);
    }

    @Override
    public void resize(int x, int y){
        this.world.resize(x, y);
        stage.getViewport().update(x, y);
        this.dialog.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight() / 5);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.Z){
            Rectangle iRectangle = this.world.player.creatInvestgateRectangle();
            RectangleMapObject collMapObject = world.getCollideObject(iRectangle, "物件層 1");
            if (collMapObject != null){
                if (collMapObject.getProperties().get("give") != null && gameStatus.getStatusHashMap().get(JsonLoaders.normalLoader.toJson(collMapObject) + collMapObject.getProperties().get("give")) == null){
                    if (gameStatus.getStatusHashMap().get(JsonLoaders.normalLoader.toJson(collMapObject) + collMapObject.getProperties().get("give")) == null)
                        gameStatus.addItem((String)collMapObject.getProperties().get("give"));
                    if (collMapObject.getProperties().get("once") == null)
                        gameStatus.getStatusHashMap().put(JsonLoaders.normalLoader.toJson(collMapObject) + collMapObject.getProperties().get("give"), true);
                    addDialog("你獲得了" + (String)collMapObject.getProperties().get("give"));
                        
                }
                else if (collMapObject.getProperties().get("description") != null){
                    addDialog(((String)collMapObject.getProperties().get("description")));
                }
                else if (collMapObject.getProperties().get("earthquack") != null){
                    world.setMap((String)collMapObject.getProperties().get("next"));
                }
                else if (collMapObject.getProperties().get("puzzle") != null){
                    PuzzleDialog p = new PuzzleDialog(this, (String)collMapObject.getProperties().get("puzzle"), (String)collMapObject.getProperties().get("reward"), (String)collMapObject.getProperties().get("word"));
                    p.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight() / 5);
                    stage.addActor(p);
                }
                else if (collMapObject.getProperties().get("store") != null){
                    game.setScreen(new StoreScreen(game, (String)collMapObject.getProperties().get("store")));
                }
                gameStatus.save();
            }
            return true;
        }
        else if(keycode == Keys.ESCAPE){
            this.world.player.freeze();
            gameStatus.currentPosition = world.player.getRectangle();
            game.setScreen(new PackageScreen(game));
        }
        return false;
    }
}

