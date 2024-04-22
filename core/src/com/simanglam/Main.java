package com.simanglam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.map.MapScreen;
import com.simanglam.scenes.InfoScreen;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen gameScreen;
	private Screen infoScreen;

	public SpriteBatch getSpriteBatch(){return this.batch;}
	public Screen getGameScreen(){return gameScreen;}
	public Screen getInfoScreen(){return infoScreen;}

	@Override
	public void create () {
		batch = new SpriteBatch();
		infoScreen = new InfoScreen(this);
		gameScreen = new MapScreen(this);
		this.setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		screen.dispose();
		batch.dispose();
	}
}

