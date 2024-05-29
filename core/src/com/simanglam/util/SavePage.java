package com.simanglam.util;

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

public class SavePage extends AbstractScreen {
    private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private BitmapFont font;
    private TextButton startButton;
    private TextButton backButton;
    private TextButton deleteButton1;
    private TextButton deleteButton2;
    private TextButton deleteButton3;
    private TextButton deleteButton4;
    private TextButton deleteButton5;
    private TextButton deleteButton6;
    private TextButton chooseButton1;
    private TextButton chooseButton2;
    private TextButton chooseButton3;
    private TextButton chooseButton4;
    private TextButton chooseButton5;
    private TextButton chooseButton6;
    


    public SavePage(final Main game) {
        batch = new SpriteBatch();
        texture = new Texture("savepage.png");
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        Texture deleteButton1Texture = new Texture(Gdx.files.internal("deletebutton.png"));
        Drawable deleteButton1Drawable = new TextureRegionDrawable(deleteButton1Texture);
        TextButton.TextButtonStyle deleteButton1Style = new TextButton.TextButtonStyle();
        deleteButton1Style.up = deleteButton1Drawable;
        deleteButton1Style.down = deleteButton1Drawable;
        deleteButton1Style.font = font;
        
        deleteButton1 = new TextButton("", deleteButton1Style);
        deleteButton1.setSize(deleteButton1Texture.getWidth() / 8.4f, deleteButton1Texture.getHeight() / 6.5f);
        deleteButton1.setPosition(Gdx.graphics.getWidth() / 2 - deleteButton1.getWidth() / 2- 149, Gdx.graphics.getHeight() / 2 - deleteButton1.getHeight() / 2 - 120);

        deleteButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture deleteButton2Texture = new Texture(Gdx.files.internal("deletebutton.png"));
        Drawable deleteButton2Drawable = new TextureRegionDrawable(deleteButton2Texture);
        TextButton.TextButtonStyle deleteButton2Style = new TextButton.TextButtonStyle();
        deleteButton2Style.up = deleteButton2Drawable;
        deleteButton2Style.down = deleteButton2Drawable;
        deleteButton2Style.font = font;
        
        deleteButton2 = new TextButton("", deleteButton2Style);
        deleteButton2.setSize(deleteButton2Texture.getWidth() / 8.4f, deleteButton2Texture.getHeight() / 6.5f);
        deleteButton2.setPosition(Gdx.graphics.getWidth() / 2 - deleteButton2.getWidth() / 2- 89, Gdx.graphics.getHeight() / 2 - deleteButton2.getHeight() / 2 - 120);

        deleteButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture deleteButton3Texture = new Texture(Gdx.files.internal("deletebutton.png"));
        Drawable deleteButton3Drawable = new TextureRegionDrawable(deleteButton3Texture);
        TextButton.TextButtonStyle deleteButton3Style = new TextButton.TextButtonStyle();
        deleteButton3Style.up = deleteButton3Drawable;
        deleteButton3Style.down = deleteButton3Drawable;
        deleteButton3Style.font = font;
        
        deleteButton3 = new TextButton("", deleteButton3Style);
        deleteButton3.setSize(deleteButton3Texture.getWidth() / 8.4f, deleteButton3Texture.getHeight() / 6.5f);
        deleteButton3.setPosition(Gdx.graphics.getWidth() / 2 - deleteButton3.getWidth() / 2- 28, Gdx.graphics.getHeight() / 2 - deleteButton3.getHeight() / 2 - 120);

        deleteButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture deleteButton4Texture = new Texture(Gdx.files.internal("deletebutton.png"));
        Drawable deleteButton4Drawable = new TextureRegionDrawable(deleteButton4Texture);
        TextButton.TextButtonStyle deleteButton4Style = new TextButton.TextButtonStyle();
        deleteButton4Style.up = deleteButton4Drawable;
        deleteButton4Style.down = deleteButton4Drawable;
        deleteButton4Style.font = font;
        
        deleteButton4 = new TextButton("", deleteButton4Style);
        deleteButton4.setSize(deleteButton4Texture.getWidth() / 8.4f, deleteButton4Texture.getHeight() / 6.5f);
        deleteButton4.setPosition(Gdx.graphics.getWidth() / 2 - deleteButton4.getWidth() / 2+ 33, Gdx.graphics.getHeight() / 2 - deleteButton4.getHeight() / 2 - 120);

        deleteButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture deleteButton5Texture = new Texture(Gdx.files.internal("deletebutton.png"));
        Drawable deleteButton5Drawable = new TextureRegionDrawable(deleteButton5Texture);
        TextButton.TextButtonStyle deleteButton5Style = new TextButton.TextButtonStyle();
        deleteButton5Style.up = deleteButton5Drawable;
        deleteButton5Style.down = deleteButton5Drawable;
        deleteButton5Style.font = font;
        
        deleteButton5 = new TextButton("", deleteButton5Style);
        deleteButton5.setSize(deleteButton5Texture.getWidth() / 8.4f, deleteButton5Texture.getHeight() / 6.5f);
        deleteButton5.setPosition(Gdx.graphics.getWidth() / 2 - deleteButton5.getWidth() / 2+ 92, Gdx.graphics.getHeight() / 2 - deleteButton5.getHeight() / 2 - 120);

        deleteButton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture deleteButton6Texture = new Texture(Gdx.files.internal("deletebutton.png"));
        Drawable deleteButton6Drawable = new TextureRegionDrawable(deleteButton6Texture);
        TextButton.TextButtonStyle deleteButton6Style = new TextButton.TextButtonStyle();
        deleteButton6Style.up = deleteButton6Drawable;
        deleteButton6Style.down = deleteButton6Drawable;
        deleteButton6Style.font = font;
        
        deleteButton6 = new TextButton("", deleteButton6Style);
        deleteButton6.setSize(deleteButton6Texture.getWidth() / 8.4f, deleteButton6Texture.getHeight() / 6.5f);
        deleteButton6.setPosition(Gdx.graphics.getWidth() / 2 - deleteButton6.getWidth() / 2+151, Gdx.graphics.getHeight() / 2 - deleteButton6.getHeight() / 2 - 120);

        deleteButton6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture chooseButton1Texture = new Texture(Gdx.files.internal("choosebutton.png"));
        Drawable chooseButton1Drawable = new TextureRegionDrawable(chooseButton1Texture);
        TextButton.TextButtonStyle chooseButton1Style = new TextButton.TextButtonStyle();
        chooseButton1Style.up = chooseButton1Drawable;
        chooseButton1Style.down = chooseButton1Drawable;
        chooseButton1Style.font = font;

                
        chooseButton1 = new TextButton("", chooseButton1Style);
        chooseButton1.setSize(chooseButton1Texture.getWidth() / 14.5f, chooseButton1Texture.getHeight() / 12f);
        chooseButton1.setPosition(Gdx.graphics.getWidth() / 2 - chooseButton1.getWidth() / 2- 149, Gdx.graphics.getHeight() / 2 - chooseButton1.getHeight() / 2 - 60);
        
        chooseButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture chooseButton2Texture = new Texture(Gdx.files.internal("choosebutton.png"));
        Drawable chooseButton2Drawable = new TextureRegionDrawable(chooseButton2Texture);
        TextButton.TextButtonStyle chooseButton2Style = new TextButton.TextButtonStyle();
        chooseButton2Style.up = chooseButton2Drawable;
        chooseButton2Style.down = chooseButton2Drawable;
        chooseButton2Style.font = font;

                
        chooseButton2 = new TextButton("", chooseButton2Style);
        chooseButton2.setSize(chooseButton2Texture.getWidth() / 14.5f, chooseButton2Texture.getHeight() / 12f);
        chooseButton2.setPosition(Gdx.graphics.getWidth() / 2 - chooseButton2.getWidth() / 2- 89, Gdx.graphics.getHeight() / 2 - chooseButton2.getHeight() / 2 - 60);
        
        chooseButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture chooseButton3Texture = new Texture(Gdx.files.internal("choosebutton.png"));
        Drawable chooseButton3Drawable = new TextureRegionDrawable(chooseButton3Texture);
        TextButton.TextButtonStyle chooseButton3Style = new TextButton.TextButtonStyle();
        chooseButton3Style.up = chooseButton3Drawable;
        chooseButton3Style.down = chooseButton3Drawable;
        chooseButton3Style.font = font;

                
        chooseButton3 = new TextButton("", chooseButton3Style);
        chooseButton3.setSize(chooseButton3Texture.getWidth() / 14.5f, chooseButton3Texture.getHeight() / 12f);
        chooseButton3.setPosition(Gdx.graphics.getWidth() / 2 - chooseButton3.getWidth() / 2- 28, Gdx.graphics.getHeight() / 2 - chooseButton3.getHeight() / 2 - 60);
        
        chooseButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture chooseButton4Texture = new Texture(Gdx.files.internal("choosebutton.png"));
        Drawable chooseButton4Drawable = new TextureRegionDrawable(chooseButton4Texture);
        TextButton.TextButtonStyle chooseButton4Style = new TextButton.TextButtonStyle();
        chooseButton4Style.up = chooseButton4Drawable;
        chooseButton4Style.down = chooseButton4Drawable;
        chooseButton4Style.font = font;

                
        chooseButton4 = new TextButton("", chooseButton4Style);
        chooseButton4.setSize(chooseButton4Texture.getWidth() / 14.5f, chooseButton4Texture.getHeight() / 12f);
        chooseButton4.setPosition(Gdx.graphics.getWidth() / 2 - chooseButton4.getWidth() / 2+ 33, Gdx.graphics.getHeight() / 2 - chooseButton4.getHeight() / 2 - 60);
        
        chooseButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture chooseButton5Texture = new Texture(Gdx.files.internal("choosebutton.png"));
        Drawable chooseButton5Drawable = new TextureRegionDrawable(chooseButton5Texture);
        TextButton.TextButtonStyle chooseButton5Style = new TextButton.TextButtonStyle();
        chooseButton5Style.up = chooseButton5Drawable;
        chooseButton5Style.down = chooseButton5Drawable;
        chooseButton5Style.font = font;

                
        chooseButton5 = new TextButton("", chooseButton5Style);
        chooseButton5.setSize(chooseButton5Texture.getWidth() / 14.5f, chooseButton5Texture.getHeight() / 12f);
        chooseButton5.setPosition(Gdx.graphics.getWidth() / 2 - chooseButton5.getWidth() / 2+ 92, Gdx.graphics.getHeight() / 2 - chooseButton5.getHeight() / 2 - 60);
        
        chooseButton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });
        Texture chooseButton6Texture = new Texture(Gdx.files.internal("choosebutton.png"));
        Drawable chooseButton6Drawable = new TextureRegionDrawable(chooseButton6Texture);
        TextButton.TextButtonStyle chooseButton6Style = new TextButton.TextButtonStyle();
        chooseButton6Style.up = chooseButton6Drawable;
        chooseButton6Style.down = chooseButton6Drawable;
        chooseButton6Style.font = font;

                
        chooseButton6 = new TextButton("", chooseButton6Style);
        chooseButton6.setSize(chooseButton6Texture.getWidth() / 14.5f, chooseButton6Texture.getHeight() / 12f);
        chooseButton6.setPosition(Gdx.graphics.getWidth() / 2 - chooseButton6.getWidth() / 2+ 151, Gdx.graphics.getHeight() / 2 - chooseButton6.getHeight() / 2 - 60);
        
        chooseButton6.addListener(new ClickListener() {
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
        stage.addActor(deleteButton1);
        stage.addActor(deleteButton2);
        stage.addActor(deleteButton3);
        stage.addActor(deleteButton4);
        stage.addActor(deleteButton5);
        stage.addActor(deleteButton6);
        stage.addActor(chooseButton1);
        stage.addActor(chooseButton2);
        stage.addActor(chooseButton3);
        stage.addActor(chooseButton4);
        stage.addActor(chooseButton5);
        stage.addActor(chooseButton6);
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