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

public class SavePage extends AbstractScreen {
    private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private BitmapFont font;
    private TextButton startButton;
    private TextButton backButton;
    private TextButton deleteButton;
    private TextButton chooseButton;
    


    public SavePage(final Main game) {
        batch = new SpriteBatch();
        texture = new Texture("savepage.png");
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        Texture deleteButtonTexture = new Texture(Gdx.files.internal("deletebutton.png"));
        Drawable deleteButtonDrawable = new TextureRegionDrawable(deleteButtonTexture);
        TextButton.TextButtonStyle deleteButtonStyle = new TextButton.TextButtonStyle();
        deleteButtonStyle.up = deleteButtonDrawable;
        deleteButtonStyle.down = deleteButtonDrawable;
        deleteButtonStyle.font = font;
        
        deleteButton = new TextButton("", deleteButtonStyle);
        deleteButton.setSize(deleteButtonTexture.getWidth() / 8.4f, deleteButtonTexture.getHeight() / 6.5f);
        deleteButton.setPosition(Gdx.graphics.getWidth() / 2 - deleteButton.getWidth() / 2- 149, Gdx.graphics.getHeight() / 2 - deleteButton.getHeight() / 2 - 120);

        deleteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture chooseButtonTexture = new Texture(Gdx.files.internal("choosebutton.png"));
        Drawable chooseButtonDrawable = new TextureRegionDrawable(chooseButtonTexture);
        TextButton.TextButtonStyle chooseButtonStyle = new TextButton.TextButtonStyle();
        chooseButtonStyle.up = chooseButtonDrawable;
        chooseButtonStyle.down = chooseButtonDrawable;
        chooseButtonStyle.font = font;

                
        chooseButton = new TextButton("", chooseButtonStyle);
        chooseButton.setSize(chooseButtonTexture.getWidth() / 14.5f, chooseButtonTexture.getHeight() / 12f);
        chooseButton.setPosition(Gdx.graphics.getWidth() / 2 - chooseButton.getWidth() / 2- 149, Gdx.graphics.getHeight() / 2 - chooseButton.getHeight() / 2 - 60);
        
        chooseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture startButtonTexture = new Texture(Gdx.files.internal("startbutton.png"));
        Drawable startButtonDrawable = new TextureRegionDrawable(startButtonTexture);
        TextButton.TextButtonStyle startButtonStyle = new TextButton.TextButtonStyle();
        startButtonStyle.up = startButtonDrawable;
        startButtonStyle.down = startButtonDrawable;
        startButtonStyle.font = font;
        
        startButton = new TextButton("", startButtonStyle);
        startButton.setSize(startButtonTexture.getWidth() / 10.0f, startButtonTexture.getHeight() / 10.0f);
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2+ 45, Gdx.graphics.getHeight() / 2 - startButton.getHeight() / 2 - 180);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });

        Texture backButtonTexture = new Texture(Gdx.files.internal("backbutton.png"));
        Drawable backButtonDrawable = new TextureRegionDrawable(backButtonTexture);
        TextButton.TextButtonStyle backButtonStyle = new TextButton.TextButtonStyle();
        backButtonStyle.up = backButtonDrawable;
        backButtonStyle.down = backButtonDrawable;
        backButtonStyle.font = font;
        
        backButton = new TextButton("", backButtonStyle);
        backButton.setSize(backButtonTexture.getWidth() / 4.4f, backButtonTexture.getHeight() / 4.8f);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2 - 45, Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 -180);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getMainPage());
            }
        });

        stage.addActor(startButton);
        stage.addActor(backButton);
        stage.addActor(deleteButton);
        stage.addActor(chooseButton);

        
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