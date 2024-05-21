package com.simanglam.fighting.bosswar;

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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

public class SelectScreen extends AbstractScreen {

    Main game;
    Stage stage;
    ArrayList<String> strings;
    GameStatus gameStatus;

    public SelectScreen(final Main game){
        this.game = game;
        stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight));
        strings = new ArrayList<>();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Table leftTable = new Table(skin);
        leftTable.left();
        gameStatus = GameStatus.getGameStatus();

        Table rightTable = new Table(skin);
        TextField pokemonNameLabel = new TextField("", skin);
        TextArea pokemonDescriptionLabel = new TextArea("", skin);
        Button startButton = new Button(skin);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (gameStatus.selectedPokemon.size() <= 0){
                    System.out.println("No enogh room");
                    return;
                }
                System.out.println(gameStatus.selectedPokemon.size());
                gameStatus.playerInventoryPokemons.forEach((Pokemon) -> {
                    System.out.println(Pokemon.getName());
                });
                game.setScreen(new BossWarScreen(game));
            }
        });

        gameStatus.playerInventoryPokemons.forEach((pokemon) -> {
            int i = 1;
            Button b = new Button(skin);
            ImageButton ib1 = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("enemies/" + pokemon.getName() + "/image/idle-0.png"))));
            ImageButton ib2 = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("character.png"))));
            ib1.setVisible(!gameStatus.selectedPokemon.contains(pokemon));
            ib2.setVisible(gameStatus.selectedPokemon.contains(pokemon));
            Stack stack = new Stack();
            b.addListener(new ClickListener(){
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                    pokemonNameLabel.setText(pokemon.getName());
                    pokemonDescriptionLabel.setText(pokemon.getDescription());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                    pokemonNameLabel.setText(" ");
                    pokemonDescriptionLabel.setText(" ");
                }

                @Override
                public void clicked(InputEvent event, float x, float y){
                    if (!gameStatus.selectedPokemon.contains(pokemon)){
                        if (gameStatus.selectedPokemon.size() >= 4){
                            System.out.println("No enogh room");
                            return ;
                        }
                        gameStatus.selectedPokemon.add(pokemon);
                    }
                    else{
                        gameStatus.selectedPokemon.remove(gameStatus.playerInventoryPokemons.indexOf(pokemon));
                    }
                    ib2.setVisible(!ib2.isVisible());
                    ib1.setVisible(!ib1.isVisible());
                }
            });
            b.add(stack);
            stack.add(ib1);
            stack.add(ib2);
            Cell<Button> c = leftTable.add(b).expandX().padBottom(20).prefSize(50);
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
        gameStatus.save();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
        this.stage.getViewport().apply();
        this.stage.getCamera().update();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(this.stage);
    }
    
}