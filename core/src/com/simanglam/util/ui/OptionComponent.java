package com.simanglam.util.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;

public class OptionComponent extends Table {
    public OptionComponent(){
        super(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class));
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);
        Button continueButton = new Button(skin);
        Button saveButton = new Button(skin);

        this.add(continueButton).fill().expand().center().prefWidth(Const.maxViewportWidth / 3).row();
        this.add(saveButton).fill().expand().center().prefWidth(Const.maxViewportWidth / 3).row();
    }
}
