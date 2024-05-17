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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;

public class MainPage extends AbstractScreen {

    private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private BitmapFont font;

    public MainPage(final Main game) {
        batch = new SpriteBatch();
        texture = new Texture("main.png"); 
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json")); 

        // ���頛�������������銝血����嗆溶�����啁�株��
        skin.add("startButtonDrawable", new Texture("startbutton.png"));

        // 雿輻�冽�啁��Drawable��萄遣������
        TextButton startButton = new TextButton("Start", skin, "default");
        Drawable startButtonDrawable = skin.getDrawable("startButtonDrawable");
        startButton.getStyle().up = startButtonDrawable; // 閮剔蔭��������芣��銝�������璅�撘�
        startButton.getStyle().down = startButtonDrawable; // 閮剔蔭���������銝�������璅�撘�

        startButton.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 155);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getGameScreen());
            }
        });

        stage.addActor(startButton);
        
        
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;

public class MainPage extends AbstractScreen {

    private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private BitmapFont font;

    public MainPage(final Main game) {
        batch = new SpriteBatch();
        texture = new Texture("main.png"); 
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json")); 

   
        TextButton startButton = new TextButton("Start", skin);


        startButton.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 25);


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("start the game");
                game.setScreen(game.getGameScreen());
            }
        });

        
        stage.addActor(startButton);

        
        Gdx.input.setInputProcessor(stage);
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
    }
}
