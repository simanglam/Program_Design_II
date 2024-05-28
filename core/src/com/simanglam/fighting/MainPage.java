package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;

public class MainPage extends AbstractScreen {

    private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private BitmapFont font;
    private TextButton startButton;
    private TextButton saveButton;

    public MainPage(final Main game) {
        batch = new SpriteBatch();
        texture = new Texture("main.png");
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        Texture startButtonTexture = new Texture(Gdx.files.internal("startbutton.png"));
        Drawable startButtonDrawable = new TextureRegionDrawable(startButtonTexture);
        TextButton.TextButtonStyle startButtonStyle = new TextButton.TextButtonStyle();
        startButtonStyle.up = startButtonDrawable;
        startButtonStyle.down = startButtonDrawable;
        startButtonStyle.font = font;
        
        startButton = new TextButton("", startButtonStyle);
        startButton.setSize(startButtonTexture.getWidth() / 10.0f, startButtonTexture.getHeight() / 10.0f);
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2+ 45, Gdx.graphics.getHeight() / 2 - startButton.getHeight() / 2 - 110);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });

        Texture saveButtonTexture = new Texture(Gdx.files.internal("save.png"));
        Drawable saveButtonDrawable = new TextureRegionDrawable(saveButtonTexture);
        TextButton.TextButtonStyle saveButtonStyle = new TextButton.TextButtonStyle();
        saveButtonStyle.up = saveButtonDrawable;
        saveButtonStyle.down = saveButtonDrawable;
        saveButtonStyle.font = font;
        
        saveButton = new TextButton("", saveButtonStyle);
        saveButton.setSize(saveButtonTexture.getWidth() / 4.6f, saveButtonTexture.getHeight() / 4.6f);
        saveButton.setPosition(Gdx.graphics.getWidth() / 2 - saveButton.getWidth() / 2 - 45, Gdx.graphics.getHeight() / 2 - saveButton.getHeight() / 2 -110);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getSavePage());
            }
        });


        stage.addActor(startButton);
        stage.addActor(saveButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        texture.dispose();
        stage.dispose();
        font.dispose();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }
}
