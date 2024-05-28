package com.simanglam.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;

public class PackageScreen extends AbstractScreen {

    Stage stage;
    Table t;
    

    public PackageScreen(){
        stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight));
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);

        t = new Table(skin);
        t.setSize(Const.maxViewportWidth, Const.maxViewportHeight);

        Stack stack = new Stack();
        stack.setSize(Const.maxViewportWidth, 7 * Const.maxViewportHeight / 8);

        Button pokemonButton = new Button(new Label("Select pokemon", skin), skin);
        Button backpackButton = new Button(new Label("背包", skin), skin);
        Button infoButton = new Button(new Label("Info", skin), skin);
        Button optionButton = new Button(new Label("選項", skin), skin);
        Table menuTable = new Table(skin);
        menuTable.add(pokemonButton, backpackButton, infoButton, optionButton);
        t.add(menuTable).top().left().row();

        stack.add(new SelectComponent(null));

        t.add(stack).top().left();
        stage.addActor(t);
    }

    @Override
    public void render(float delta) {
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
