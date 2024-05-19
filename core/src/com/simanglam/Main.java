package com.simanglam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simanglam.fighting.bosswar.BossWarScreen;
import com.simanglam.fighting.bosswar.SelectScreen;
import com.simanglam.map.MapScreen;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.InfoScreen;
import com.simanglam.fighting.PokemonScreen;
import com.simanglam.fighting.MainPage;

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
		batch = new SpriteBatch();
		infoScreen = new InfoScreen(this);
		gameScreen = new MapScreen(this);
		bossWarScreen = new BossWarScreen(this);
		pokemonScreen = new PokemonScreen(this);
        mainPage = new MainPage(this);
		this.setScreen(pokemonScreen);
		bossWarScreen = new BossWarScreen(this, new String[]{"base"});
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
