package com.simanglam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.fighting.MainPage;
import com.simanglam.fighting.bosswar.BossWarScreen;
import com.simanglam.fighting.bosswar.SelectScreen;
import com.simanglam.map.MapScreen;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.GameStatus;
import com.simanglam.util.InfoScreen;

public class Main extends Game {
	private SpriteBatch batch;
	private Screen gameScreen;
	private Screen infoScreen;
	private Screen bossWarScreen;
	private Screen pokemonScreen;
    private Screen mainPage;

	public SpriteBatch getSpriteBatch(){return this.batch;}
	public Screen getGameScreen(){return gameScreen;}
	public Screen getInfoScreen(){return infoScreen;}
	public Screen getBossWarScreen(){return bossWarScreen;}
	public Screen getPokemonScreen(){return pokemonScreen;}
    public Screen getMainPage(){return mainPage;}

	@Override
	public void create () {
		GameStatus gameStatus = GameStatus.getGameStatus();
		batch = new SpriteBatch();
		infoScreen = new InfoScreen(this);
		gameScreen = new MapScreen(this);
		//pokemonScreen = new PokemonScreen(this);
        mainPage = new MainPage(this);
		bossWarScreen = new BossWarScreen(this);
		this.setScreen(new SelectScreen(this));
	}

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        ((AbstractScreen)screen).handleInput();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
    }
}
