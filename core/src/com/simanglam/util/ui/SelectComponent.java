package com.simanglam.util.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;
import com.simanglam.util.InventoryPokemon;

public class SelectComponent extends Table {

    ArrayList<String> strings;
    GameStatus gameStatus;
    AssetsManagerWrapper assetsManagerWrapper;

    public SelectComponent(){
        super(AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class));
        assetsManagerWrapper = AssetsManagerWrapper.getAssetsManagerWrapper();
        strings = new ArrayList<>();
        Skin skin = assetsManagerWrapper.assetManager.get("data/uiskin.json", Skin.class);
        Table leftTable = new Table(skin);
        leftTable.left().top();
        gameStatus = GameStatus.getGameStatus();

        Table rightTable = new Table(skin);
        TextField pokemonNameLabel = new TextField("", skin);
        TextArea pokemonDescriptionLabel = new TextArea("", skin);
        int i = 1;
        for (InventoryPokemon pokemon : gameStatus.playerInventoryPokemons) {
            Button b = new Button(skin);
            ImageButton ib1 = new ImageButton(new TextureRegionDrawable(assetsManagerWrapper.assetManager.get("enemies/" + pokemon.getName() + "/image/icon.png", Texture.class)));
            ImageButton ib2 = new ImageButton(new TextureRegionDrawable(assetsManagerWrapper.assetManager.get("character.png", Texture.class)));
            ib1.setVisible(!gameStatus.selectedPokemon.contains(pokemon));
            ib2.setVisible(gameStatus.selectedPokemon.contains(pokemon));
            Stack stack = new Stack();
            b.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    pokemonNameLabel.setText(pokemon.getName());
                    pokemonDescriptionLabel.setText(pokemon.getDescription());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    pokemonNameLabel.setText(" ");
                    pokemonDescriptionLabel.setText(" ");
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!gameStatus.selectedPokemon.contains(pokemon)) {
                        if (gameStatus.selectedPokemon.size() >= 4) {
                            System.out.println("No enogh room");
                            return;
                        }
                        gameStatus.selectedPokemon.add(pokemon);
                    } else {
                        gameStatus.selectedPokemon.remove(gameStatus.playerInventoryPokemons.indexOf(pokemon));
                    }
                    ib2.setVisible(!ib2.isVisible());
                    ib1.setVisible(!ib1.isVisible());
                }
            });
            b.add(stack);
            stack.add(ib1);
            stack.add(ib2);
            Cell<Button> c = leftTable.add(b).expandX().padBottom(20).prefSize(50).left();
            if (i % 4 == 0) {
                i = 0;
                c.row();
            }
            i++;
        }
        rightTable.add(pokemonNameLabel).row();
        rightTable.add(pokemonDescriptionLabel).prefHeight(Const.maxViewportHeight).row();

        Table outterTable = new Table(skin);
        outterTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        outterTable.add(leftTable).prefSize(2 * Const.maxViewportWidth / 3, Const.maxViewportHeight);
        outterTable.add(rightTable);
        this.add(outterTable);
    }
}