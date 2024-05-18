package com.simanglam.fighting.bosswar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;

public class BossWarScreen extends AbstractScreen {
    Main game;
    BossWarWorld world;
    BossWarActor[] playerPokemons;
    Stage stage;
    Camera camera;
    Label label;
    InputMultiplexer inputMultiplexer;

    public BossWarScreen(final Main game, String[] strings){
        this.game = game;
        this.world = new BossWarWorld();
        this.stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera()));
        this.camera = new OrthographicCamera();
        label = new Label("16500/16500" ,new Skin(Gdx.files.internal("data/uiskin.json")));
        label.setPosition(Const.maxViewportWidth - label.getWidth(), Const.maxViewportHeight - label.getHeight());
        this.inputMultiplexer = new InputMultiplexer();

        playerPokemons = new BossWarActor[]{null, null, null, null};
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Table table = new Table(skin);
        ProgressBar[] progressBars = new ProgressBar[4];
        Button[] buttons = new Button[4];
        ImageButton[] imageButtons = new ImageButton[4];
        
        for (int i = 0; i < 4; i++){
            buttons[i] = new Button(skin);
            table.add(buttons[i]).prefSize(60).expandX();
            if (i >= strings.length){
                buttons[i].setDisabled(true);
                continue;
            }
            progressBars[i] = new ProgressBar(0, 1, 0.2f, false, skin);
            progressBars[i].setVisible(false);
            playerPokemons[i] = new BossWarActor("enemies/" + strings[i], false);
            imageButtons[i] = new ImageButton(new TextureRegionDrawable(new Texture("enemies/" + strings[i] + "/image/idle-0.png")));
            buttons[i].add(imageButtons[i]).row();
            buttons[i].add(progressBars[i]).width(60).row();
            Label moneyLabel = new Label(String.valueOf(playerPokemons[i].money), skin);
            buttons[i].add(moneyLabel).right();
            final int j = i;
            buttons[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (world.money < playerPokemons[j].money) return;
                    world.money -= playerPokemons[j].money;
                    world.addPlayerPokemon(playerPokemons[j]);
                    buttons[j].addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.delay(playerPokemons[j].spawnCooldown), Actions.touchable(Touchable.enabled)));
                    progressBars[j].addAction(Actions.sequence(Actions.show(),
                    Actions.run(() -> {progressBars[j].setAnimateDuration(playerPokemons[j].spawnCooldown); progressBars[j].setValue(10);}),
                    Actions.delay(playerPokemons[j].spawnCooldown), Actions.hide(),
                    Actions.run(() -> {progressBars[j].setAnimateDuration(0);progressBars[j].setValue(0);}))
                    );
                }
            });
        }

        table.setBounds(0, 25, Const.maxViewportWidth, 75);
        table.center();
        this.stage.addActor(label);
        this.stage.addActor(table);

        this.inputMultiplexer.addProcessor(stage);
        this.inputMultiplexer.addProcessor(world);
    }

    @Override
    public void handleInput(){
        Gdx.input.setInputProcessor(this.inputMultiplexer);
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 0);
        this.world.update(delta, game.getSpriteBatch());
        this.stage.act(delta);
        label.setText(String.valueOf(world.getMoney()) + "/16500");
        this.stage.getViewport().apply();
        this.stage.draw();
    }

    @Override
    public void resize(int x, int y){
        this.stage.getViewport().update(x, y);
        world.resize(x, y);
    }

    @Override
    public void pause(){};
    @Override
    public void show(){};
    @Override
    public void hide(){};
    @Override
    public void resume(){};
    @Override
    public void dispose(){};
}