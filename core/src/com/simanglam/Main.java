package com.simanglam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.scenes.MainMenu;

public class Main extends Game {
	private SpriteBatch batch;

	public SpriteBatch getSpriteBatch(){return this.batch;}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
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

