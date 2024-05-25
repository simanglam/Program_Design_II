package com.simanglam.fighting.bosswar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

public class BossWarScreen extends AbstractScreen {
    Main game;
    BossWarWorld world;
    BossWarActor[] playerPokemons;
    Stage currentStage;
    Stage fightStage;
    Stage winStage;
    Stage loseStage;
    Label label;
    InputMultiplexer inputMultiplexer;

    public BossWarScreen(final Main game){
        this.game = game;
        this.world = new BossWarWorld();
        Viewport viewport = new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera());
        this.fightStage = new Stage(viewport);
        label = new Label("16500/16500", new Skin(Gdx.files.internal("data/uiskin.json")));
        label.setPosition(Const.maxViewportWidth - label.getWidth(), Const.maxViewportHeight - label.getHeight());
        this.inputMultiplexer = new InputMultiplexer();

        playerPokemons = new BossWarActor[]{null, null, null, null};
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Table table = new Table(skin);
        ProgressBar[] progressBars = new ProgressBar[4];
        Button[] buttons = new Button[4];
        ImageButton[] imageButtons = new ImageButton[4];
        GameStatus gameStatus = GameStatus.getGameStatus();

        for (int i = 0; i < 4; i++){
            buttons[i] = new Button(skin);
            table.add(buttons[i]).prefSize(60).expandX();
            if (i >= gameStatus.selectedPokemon.size()){
                buttons[i].setDisabled(true);
                continue;
            }
            Stack stack = new Stack();
            progressBars[i] = new ProgressBar(0, 1, 0.2f, false, skin);
            progressBars[i].setVisible(false);
            playerPokemons[i] = new BossWarActor("enemies/" + gameStatus.selectedPokemon.get(i).getName(), false);
            imageButtons[i] = new ImageButton(new TextureRegionDrawable(new Texture("enemies/" + gameStatus.selectedPokemon.get(i).getName() + "/image/idle-0.png")));
            buttons[i].add(imageButtons[i]).row();
            Label moneyLabel = new Label(String.valueOf(playerPokemons[i].money), skin);
            buttons[i].add(stack).width(60);
            stack.add(progressBars[i]);
            stack.add(moneyLabel);
            final int j = i;
            buttons[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (world.money < playerPokemons[j].money) return;
                    world.money -= playerPokemons[j].money;
                    world.addPlayerPokemon(playerPokemons[j]);
                    buttons[j].addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.delay(playerPokemons[j].spawnCooldown), Actions.touchable(Touchable.enabled)));
                    progressBars[j].addAction(Actions.sequence(
                    Actions.show(),
                    Actions.run(() -> {progressBars[j].setAnimateDuration(playerPokemons[j].spawnCooldown); progressBars[j].setValue(10); moneyLabel.setVisible(false);}),
                    Actions.delay(playerPokemons[j].spawnCooldown), Actions.hide(),
                    Actions.run(() -> {progressBars[j].setAnimateDuration(0); progressBars[j].setValue(0); moneyLabel.setVisible(true);})
                    ));
                }
            });
        }


        table.setBounds(0, 25, Const.maxViewportWidth, 75);
        table.center();
        this.fightStage.addActor(label);
        this.fightStage.addActor(table);
        currentStage = fightStage;

        winStage = new Stage(viewport);
        Table winTable = new Table(skin);
        Button winButton = new Button(skin);
        winButton.add("你贏了");
        winButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(game.getGameScreen());
            }
        });
        winTable.add(winButton).center();
        winTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        winStage.addActor(winTable);
        loseStage = winStage;

        loseStage = new Stage(viewport);
        Table loseTable = new Table(skin);
        Button loseButton = new Button(skin);
        loseButton.add("你輸了");
        loseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(game.getGameScreen());
            }
        });
        loseTable.add(loseButton).center();
        loseTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        loseStage.addActor(loseTable);

        this.inputMultiplexer.addProcessor(currentStage);
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
        this.currentStage.act(delta);
        label.setText(String.valueOf(world.getMoney()) + "/16500");
        this.currentStage.getViewport().apply();
        this.currentStage.draw();
        if (world.win() && currentStage != winStage){
            currentStage = winStage;
            inputMultiplexer.clear();
            inputMultiplexer.addProcessor(winStage);
        }
        else if (world.lose() && currentStage != loseStage){
            currentStage = loseStage;
            inputMultiplexer.clear();
            inputMultiplexer.addProcessor(loseStage);
        }
    }

    @Override
    public void resize(int x, int y){
        this.currentStage.getViewport().update(x, y);
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
    public void dispose(){
        fightStage.dispose();
        winStage.dispose();
        loseStage.dispose();
        world.dispose();
    };
}