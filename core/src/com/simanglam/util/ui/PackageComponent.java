package com.simanglam.util.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

public class PackageComponent extends Table{
    public PackageComponent(){
        super(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class));
        AssetsManagerWrapper assetsManagerWrapper = AssetsManagerWrapper.getAssetsManagerWrapper();
        Skin skin = assetsManagerWrapper.assetManager.get("data/uiskin.json", Skin.class);
        Table upperTable = new Table(skin);
        GameStatus gameStatus = GameStatus.getGameStatus();

        Table lowerTable = new Table(skin);
        TextField pokemonNameLabel = new TextField("", skin);
        TextArea pokemonDescriptionLabel = new TextArea("", skin);

        gameStatus.playerInventory.forEach((item) -> {
            int i = 1;
            Button b = new Button(skin);
            ImageButton ib1 = new ImageButton(new TextureRegionDrawable(assetsManagerWrapper.assetManager.get("items/" + item.getName() + "/icon.png", Texture.class)));
            b.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    pokemonNameLabel.setText(item.getName());
                    pokemonDescriptionLabel.setText(item.description());
                }
            });
            Cell<Button> c = lowerTable.add(b).expandX().padBottom(20).prefSize(20).left();
            b.add(ib1);
            b.add(new Label(item.getName(), skin));
            if (i % 2 == 0){
                i = 0;
                c.row();
            }
            i++;
        });

        upperTable.add(pokemonDescriptionLabel).prefHeight(Const.maxViewportHeight / 6).prefWidth(Const.maxViewportWidth).row();

        Table outterTable = new Table(skin);
        outterTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        outterTable.add(upperTable).prefSize(Const.maxViewportWidth, Const.maxViewportHeight / 6).row();
        outterTable.add(lowerTable).expandY();
        this.add(outterTable);
    }
}
