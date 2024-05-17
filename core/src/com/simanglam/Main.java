package com.simanglam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.fighting.bosswar.BossWarScreen;
import com.simanglam.map.MapScreen;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.InfoScreen;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen gameScreen;
	private Screen infoScreen;
	private Screen bossWarScreen;

	public SpriteBatch getSpriteBatch(){return this.batch;}
	public Screen getGameScreen(){return gameScreen;}
	public Screen getInfoScreen(){return infoScreen;}
	public Screen getBossWarScreen(){return bossWarScreen;}

	@Override
	public void create () {
		batch = new SpriteBatch();
		infoScreen = new InfoScreen(this);
		gameScreen = new MapScreen(this);
		bossWarScreen = new BossWarScreen(this);
<<<<<<< Updated upstream
		this.setScreen(getBossWarScreen());
=======
		pokemonScreen = new PokemonScreen(this);
        mainPage = new MainPage(this);
		this.setScreen(mainPage);
>>>>>>> Stashed changes
	}

	@Override
	public void setScreen(Screen screen){
		super.setScreen(screen);
		((AbstractScreen)screen).handleInput();
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

