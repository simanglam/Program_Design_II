package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;

public class PokemonScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Texture map;
    private Texture pokemon;
    private Stage stage;

    public PokemonScreen(Main game) {
        batch = new SpriteBatch();
        map = new Texture("bosswar.png");
        pokemon = new Texture("enemies/base/image/idle-0.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

    
        TextButton backpackButton = new TextButton("Backpack", skin);
        TextButton skillButton = new TextButton("Skill", skin);
        TextButton pokemonButton = new TextButton("Pokemon", skin);
        TextButton escapeButton = new TextButton("Escape", skin);

     
        float buttonWidth = 200f;
        float buttonHeight = 50f;
        backpackButton.setSize(buttonWidth, buttonHeight);
        skillButton.setSize(buttonWidth, buttonHeight);
        pokemonButton.setSize(buttonWidth, buttonHeight);
        escapeButton.setSize(buttonWidth, buttonHeight);


        float buttonSpacing = 20f;
        float buttonX = buttonSpacing;
        float buttonY = Gdx.graphics.getHeight() - buttonHeight - buttonSpacing;
        backpackButton.setPosition(buttonX, buttonY);
        skillButton.setPosition(buttonX, buttonY - buttonHeight - buttonSpacing);
        pokemonButton.setPosition(buttonX, buttonY - 2 * (buttonHeight + buttonSpacing));
        escapeButton.setPosition(buttonX, buttonY - 3 * (buttonHeight + buttonSpacing));

        backpackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("Backpack button clicked!");
            }
        });

        skillButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Skill button clicked!");
            }
        });

        pokemonButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Pokemon button clicked!");
            }
        });

        escapeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("Escape button clicked!");
            }
        });

 
        stage.addActor(backpackButton);
        stage.addActor(skillButton);
        stage.addActor(pokemonButton);
        stage.addActor(escapeButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(map, 0, 0);
        batch.draw(pokemon, 50, 120);
        batch.draw(pokemon, 600, 120);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        stage.dispose();
    }

    @Override
    public void handleInput() {

    }
}
