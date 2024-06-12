package com.simanglam.util.ui;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simanglam.fighting.Pokemon;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;
import com.simanglam.util.InventoryItem;

public class PackageComponent extends Table{

    TextArea pokemonDescriptionLabel;
    Table upperTable;

    public PackageComponent(){
        super(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class));
        AssetsManagerWrapper assetsManagerWrapper = AssetsManagerWrapper.getAssetsManagerWrapper();
        Skin skin = assetsManagerWrapper.assetManager.get("data/uiskin.json", Skin.class);
        upperTable = new Table(skin);
        GameStatus gameStatus = GameStatus.getGameStatus();
        gameStatus.playerInventory.sort(Comparator.comparing(InventoryItem::getName));
        Button nameReverseButton = new Button(new Label("依名字排序", skin), skin);
        Button numberReversqButton = new Button(new Label("依數量排序", skin), skin);

        Table lowerTable = new Table(skin);
        lowerTable.setWidth(Const.maxViewportWidth);
        pokemonDescriptionLabel = new TextArea("", skin);

        initLowerTable(lowerTable);

        nameReverseButton.addListener(new ClickListener() {
            boolean reverse = false;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (reverse)
                    gameStatus.playerInventory.sort(Comparator.comparing(InventoryItem::getName));
                else
                    gameStatus.playerInventory.sort(Comparator.comparing(InventoryItem::getName).reversed());
                reverse = !reverse;
                lowerTable.clear();
                initLowerTable(lowerTable);
           }
        });

        numberReversqButton.addListener(new ClickListener() {
            boolean reverse = false;
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (reverse)
                    gameStatus.playerInventory.sort(Comparator.comparing(InventoryItem::getNum));
                else
                    gameStatus.playerInventory.sort(Comparator.comparing(InventoryItem::getNum).reversed());
                reverse = !reverse;
                lowerTable.clear();
                initLowerTable(lowerTable);
           }
        });
        upperTable.add(nameReverseButton).prefHeight(Const.maxViewportHeight / 6f).prefWidth(Const.maxViewportWidth / 2);
        upperTable.add(numberReversqButton).prefHeight(Const.maxViewportHeight / 6f).prefWidth(Const.maxViewportWidth / 2);
        lowerTable.top().left();
        Table outterTable = new Table(skin);
        outterTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        outterTable.add(pokemonDescriptionLabel).prefSize(Const.maxViewportWidth, Const.maxViewportHeight / 6f).row();
        outterTable.add(upperTable).prefSize(Const.maxViewportWidth, Const.maxViewportHeight / 12f).row();
        outterTable.add(lowerTable).prefSize(Const.maxViewportWidth, 5 *Const.maxViewportHeight / 6f).row();
        outterTable.top().left();
        this.add(outterTable);
    }

    private void initLowerTable(Table lowerTable) {
        int i = 1;
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);
        for (InventoryItem item : GameStatus.getGameStatus().playerInventory) {
            Button b = new Button(skin);
            ImageButton ib1 = new ImageButton(new TextureRegionDrawable(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("items/" + item.getName() + "/icon.png", Texture.class)));
            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pokemonDescriptionLabel.setText(item.description());
                }
            });
            Cell<Button> c = lowerTable.add(b).expandX().padBottom(20).prefSize(20).left();
            b.add(ib1);
            b.add(new Label(String.format("%.5s %d", item.getName(), item.getNum()), skin));
            if (i % 4 == 0){
                i = 0;
                c.row();
            }
            i++;
        }

    }
}
