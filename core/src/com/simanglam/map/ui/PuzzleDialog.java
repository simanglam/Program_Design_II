package com.simanglam.map.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class PuzzleDialog extends Window {

    public PuzzleDialog() {
        super("sa", new Skin(Gdx.files.internal("data/uiskin.json")));
    }
    
}
