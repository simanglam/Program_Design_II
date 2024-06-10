package com.simanglam.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;

public class PackageScreen extends AbstractScreen {
    Stage stage;
    Table t;
    

    public PackageScreen(final Main game){
        stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera()));
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);

        t = new Table(skin);
        t.setSize(Const.maxViewportWidth, Const.maxViewportHeight);

        Stack stack = new Stack();
        stack.setSize(Const.maxViewportWidth, 7 * Const.maxViewportHeight / 8);

        OptionComponent optionComponent = new OptionComponent(game);
        optionComponent.setVisible(false);
        PackageComponent packageComponent = new PackageComponent();
        packageComponent.setVisible(false);
        SelectComponent selectComponent = new SelectComponent();
        selectComponent.setVisible(false);
        InfoComponent infoComponent = new InfoComponent();
        infoComponent.setVisible(true);

        Button pokemonButton = new Button(new Label("Select pokemon", skin), skin);
        Button backpackButton = new Button(new Label("背包", skin), skin);
        Button infoButton = new Button(new Label("Info", skin), skin);
        Button optionButton = new Button(new Label("選項", skin), skin);

        pokemonButton.addListener(new ClickListener(){
            @Override
                public void clicked(InputEvent event, float x, float y){
                    selectComponent.setVisible(true);
                    packageComponent.setVisible(false);
                    optionComponent.setVisible(false);
                    infoComponent.setVisible(false);
                }
        });

        infoButton.addListener(new ClickListener(){
            @Override
                public void clicked(InputEvent event, float x, float y){
                    selectComponent.setVisible(false);
                    packageComponent.setVisible(false);
                    optionComponent.setVisible(false);
                    infoComponent.setVisible(true);
                }
        });

        backpackButton.addListener(new ClickListener(){
            @Override
                public void clicked(InputEvent event, float x, float y){
                    selectComponent.setVisible(false);
                    packageComponent.setVisible(true);
                    optionComponent.setVisible(false);
                    infoComponent.setVisible(false);
                }
        });

        optionButton.addListener(new ClickListener(){
            @Override
                public void clicked(InputEvent event, float x, float y){
                    selectComponent.setVisible(false);
                    packageComponent.setVisible(false);
                    optionComponent.setVisible(true);
                    infoComponent.setVisible(false);
                }
        });

        Table menuTable = new Table(skin);
        menuTable.add(pokemonButton, backpackButton, infoButton, optionButton);
        t.add(menuTable).top().left().row();

        stack.add(optionComponent);
        stack.add(packageComponent);
        stack.add(selectComponent);
        stack.add(infoComponent);

        t.add(stack).center().expand();
        stage.addActor(t);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getViewport().apply();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(this.stage);
    }
    
}
