package com.simanglam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.LoadingScreen;

public class Main extends Game {
    private SpriteBatch batch;
    private Screen gameScreen;

	public SpriteBatch getSpriteBatch(){return this.batch;}
	public Screen getGameScreen(){return gameScreen;}
	public void setGameScreen(Screen s){gameScreen = s;}

	@Override
	public void create(){
		this.setScreen(new LoadingScreen(this));
		batch = new SpriteBatch();
	}

    @Override
    public void setScreen(Screen screen){
        super.setScreen(screen);
        ((AbstractScreen)screen).handleInput();
    }

    @Override
    public void render(){
        super.render();
    }

    @Override
    public void dispose(){
        screen.dispose();
        batch.dispose();
    }
}