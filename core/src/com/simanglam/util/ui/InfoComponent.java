package com.simanglam.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.simanglam.util.GameStatus;

public class InfoComponent extends Table {

    public InfoComponent(){
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Label moneyLabel = new Label("Money: " + String.valueOf(GameStatus.getGameStatus().money), skin);
        moneyLabel.addAction(Actions.color(Color.BLACK));
        moneyLabel.setFontScale(2);
        this.add(moneyLabel);
    }
}