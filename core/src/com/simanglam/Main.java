import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    private SpriteBatch batch;
    private Screen gameScreen;
    private Screen infoScreen;
    private Screen bossWarScreen;
    private Screen pokemonBattleScreen;

    public SpriteBatch getSpriteBatch() {
        return this.batch;
    }

    public Screen getGameScreen() {
        return gameScreen;
    }

    public Screen getInfoScreen() {
        return infoScreen;
    }

    public Screen getBossWarScreen() {
        return bossWarScreen;
    }

    public Screen getPokemonBattleScreen() {
        return pokemonBattleScreen;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        infoScreen = new InfoScreen(this);
        gameScreen = new MapScreen(this);
        bossWarScreen = new BossWarScreen(this);
        pokemonBattleScreen = new PokemonBattleScreen(this);
        this.setScreen(pokemonBattleScreen);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        // 可以在這裡添加任何必要的處理
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
