package com.simanglam.store;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

public class StoreScreen extends AbstractScreen {

    Main game;
    Texture background;
    GameStatus gameStatus;
    Stage stage;
    ArrayList<StoreItem> storeItems;

    public StoreScreen(final Main game, String path){
        this.game = game;
        Json loader = new Json();

        storeItems = loader.fromJson(ArrayList.class, StoreItem.class, Gdx.files.internal("store/" + path + ".json"));
        gameStatus = GameStatus.getGameStatus();

        stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera(Const.maxViewportWidth, Const.maxViewportHeight)));
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);
        background = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("store_background.png", Texture.class);
        Table rightTable = new Table(skin);
        Label descriptionLabel = new Label(" ", skin);
        Button descriptionButton = new Button(descriptionLabel, skin);
        Button buyButton = new Button(new Label("已買", skin), skin);
        descriptionButton.setDisabled(true);
        descriptionButton.setVisible(false);
        buyButton.setVisible(false);
        buyButton.setDisabled(true);
        Button startButton = new Button(new Label("離開", skin), skin);
        Table leftTable = new Table(skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(game.getGameScreen());
            }
        });
        int i = 1;
        for (StoreItem item: storeItems){
            ImageButton b = new ImageButton(new TextureRegionDrawable(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("store_item_background.png", Texture.class)));
            ImageButton ib1 = new ImageButton(new TextureRegionDrawable(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("items/" + item.getName() + "/icon.png", Texture.class)));
            Table t = new Table(skin);
            Stack s = new Stack();
            b.addListener(new ClickListener(){
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                    descriptionLabel.setText(item.description);
                    descriptionButton.setWidth(descriptionLabel.getWidth());
                    descriptionButton.setPosition(Math.max(0, Math.min(event.getStageX() - descriptionButton.getWidth() / 2, Const.maxViewportWidth - descriptionButton.getWidth())), event.getStageY() + (descriptionButton.getHeight() / 2));
                    descriptionButton.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                    descriptionButton.setVisible(false);
                }

                @Override
                public void clicked(InputEvent event, float x, float y){
                    if (gameStatus.money < item.getPrice()) return;
                    gameStatus.addItem(item.getName());
                    gameStatus.money -= item.getPrice();
                    buyButton.clearActions();
                    buyButton.setVisible(true);
                    buyButton.setPosition(Math.max(0, Math.min(event.getStageX() - buyButton.getWidth() / 2, stage.getWidth() - buyButton.getWidth() / 2)), event.getStageY() + (buyButton.getHeight() / 2));
                    buyButton.addAction(Actions.sequence(Actions.delay(2), Actions.hide()));
                }
            });
            t.add(ib1).expand();
            t.row();
            t.add(new Label(String.valueOf(item.getPrice()), skin)).right();
            s.add(b);
            s.add(t);
            Cell<Stack> c = leftTable.add(s).expand().prefSize(50).center();
            if (i % 4 == 0){
                i = 0;
                c.row();
            }
            i++;
        }

        Table outterTable = new Table(skin);
        outterTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        outterTable.add(leftTable).prefSize(Const.maxViewportWidth, Const.maxViewportHeight);
        outterTable.add(rightTable);
        stage.addActor(outterTable);
        stage.addActor(descriptionButton);
        stage.addActor(buyButton);
        startButton.setX(Const.maxViewportWidth, Align.right);
        stage.addActor(startButton);
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        game.getSpriteBatch().setProjectionMatrix(stage.getCamera().combined);
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(background, 0, 0);
        game.getSpriteBatch().end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height);
        stage.getCamera().update();
    }

    @Override
    public void handleInput(){
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose(){
        stage.dispose();
    }
    
}

