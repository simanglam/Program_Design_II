package com.simanglam.store;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

public class StoreScreen extends AbstractScreen {

    Main game;
    GameStatus gameStatus;
    Stage stage;
    ArrayList<StoreItem> storeItems;

    public StoreScreen(final Main game){
        this.game = game;
        Json loader = new Json();

        loader.setElementType(StoreList.class, "items", StoreItem.class);
        storeItems = loader.fromJson(ArrayList.class, StoreItem.class, Gdx.files.internal("store/" + "test" + ".json"));
        gameStatus = GameStatus.getGameStatus();

        stage = new Stage(new ExtendViewport(Const.maxViewportWidth, Const.maxViewportWidth));
        Skin skin = game.assetManager.get("data/uiskin.json", Skin.class);
        Table rightTable = new Table(skin);
        TextField pokemonNameLabel = new TextField("", skin);
        TextArea pokemonDescriptionLabel = new TextArea("", skin);
        Button startButton = new Button(skin);
        Table leftTable = new Table(skin);
        leftTable.left().top();
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(game.getGameScreen());
            }
        });

        storeItems.forEach((item) -> {
            int i = 1;
            Button b = new Button(skin);
            ImageButton ib1 = new ImageButton(new TextureRegionDrawable(game.assetManager.get("items/" + item.getName() + "/icon.png", Texture.class)));
            b.addListener(new ClickListener(){
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                    pokemonNameLabel.setText(item.getName());
                    pokemonDescriptionLabel.setText(item.getDescription());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                    pokemonNameLabel.setText(" ");
                    pokemonDescriptionLabel.setText(" ");
                }

                @Override
                public void clicked(InputEvent event, float x, float y){
                    if (gameStatus.money < item.getPrice()) return;
                    gameStatus.addItem(item.getName());
                    gameStatus.money -= item.getPrice();
                }
            });
            b.add(ib1);
            b.row();
            b.add(String.valueOf(item.getPrice())).right();
            Cell<Button> c = leftTable.add(b).expandX().padBottom(20).prefSize(50).left();
            if (i % 4 == 0){
                i = 0;
                c.row();
            }
            i++;
        });
        startButton.add(new Label("Start!", skin));
        rightTable.add(pokemonNameLabel).row();
        rightTable.add(pokemonDescriptionLabel).prefHeight(Const.maxViewportHeight).row();
        rightTable.add(startButton).expandX();

        Table outterTable = new Table(skin);
        outterTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        outterTable.add(leftTable).prefSize(2 * Const.maxViewportWidth / 3, Const.maxViewportHeight);
        outterTable.add(rightTable);
        stage.addActor(outterTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act();
        stage.draw();
        gameStatus.save();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }
    
}

