package com.simanglam.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simanglam.Main;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

public class OptionComponent extends Table {
    public OptionComponent(final Main game){
        super(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class));
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);
        Button continueButton = new Button(skin);
        continueButton.add("繼續遊戲");
        Button saveButton = new Button(skin);
        saveButton.add("存檔");
        Button quitButton = new Button(skin);
        quitButton.add("退出");

        continueButton.addListener(new ClickListener(){
            @Override
                public void clicked(InputEvent event, float x, float y){
                    game.setScreen(game.getGameScreen());
                }
        });

        saveButton.addListener(new ClickListener(){
            @Override
                public void clicked(InputEvent event, float x, float y){
                    GameStatus.getGameStatus().save();
                }
        });

        quitButton.addListener(new ClickListener(){
            @Override
                public void clicked(InputEvent event, float x, float y){
                    Gdx.app.exit();
                }
        });

        this.add(continueButton).center().prefWidth(Const.maxViewportWidth / 3).row();
        this.add(saveButton).center().prefWidth(Const.maxViewportWidth / 3).row();
    }
}
