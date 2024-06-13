package com.simanglam.fighting;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simanglam.Main;
import com.simanglam.fighting.skillbehavior.SkillBehavior;
import com.simanglam.map.ui.Dialog;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;
import com.simanglam.util.InventoryItem;

public class FightingScreen extends AbstractScreen {

    Stage stage;
    GameStatus gameStatus;
    Main game;
    Viewport viewport;

    Button[] pokemonButtons;
    Table skillTable;
    Table switchTable;
    Table optionTable;
    Table packageTable;
    Dialog dialog;
    BitmapFont font;

    Button resultButton;

    Pokemon enemy;
    Pokemon[] playerPokemons;
    Pokemon currentPokemon;

    Texture background;
    public FightingScreen(Main game) {
        this.viewport = new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, new OrthographicCamera(Const.maxViewportWidth, Const.maxViewportHeight));
        viewport.apply();
        this.stage = new Stage(viewport);
        this.gameStatus = GameStatus.getGameStatus();
        this.game = game;
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);
        font = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/font/font.fnt", BitmapFont.class);
        background = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("FTbg.jpg", Texture.class);

        resultButton = new Button(skin);
        resultButton.add("你輸了");
        resultButton.setSize(72, 20);
        resultButton.setPosition((Const.maxViewportWidth - resultButton.getWidth()) / 2f, (Const.maxViewportHeight - resultButton.getHeight()) / 2f);
        this.enemy = PokemonFactory.buildPokemon("base");
        Table finalTable = new Table();
        skillTable = new Table(skin);
        switchTable = new Table(skin);
        optionTable = new Table(skin);
        packageTable = new Table(skin);

        optionTable.setSize(Const.maxViewportWidth / 4f, Const.maxViewportHeight / 4f);

        finalTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        Stack stack = new Stack();
        Button[] skillButtons = new Button[4];

        dialog = new Dialog();
        dialog.setVisible(false);
        dialog.setWidth(Const.maxViewportWidth);
        dialog.setFillParent(true);
        dialog.setMovable(false);
        stack.add(dialog);
        playerPokemons = new Pokemon[4];
        for (int i = 0; i < 4 && i < gameStatus.selectedPokemon.size(); i++)
            playerPokemons[i] = PokemonFactory.buildPokemon(gameStatus.selectedPokemon.get(i).getName());
        pokemonButtons = new Button[4];
        currentPokemon = playerPokemons[0];
        for (int i = 0; i < 4; i++) {
            pokemonButtons[i] = new Button(skin);
            if (playerPokemons[i] == null) {
                pokemonButtons[i].setDisabled(true);
                pokemonButtons[i].add("No Pokemon");
                continue;
            }
            pokemonButtons[i].add("Pokemon");
            final int j = i;
            pokemonButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentPokemon = playerPokemons[j];
                    switchTable.setVisible(false);
                    skillTable.setVisible(false);
                    optionTable.setVisible(true);
                    dialog.setVisible(true);
                    dialog.setDescription(String.format("換成了%s", currentPokemon.getName()));
                    dialog.addAction(Actions.sequence(Actions.delay(2), Actions.hide(), Actions.run(() -> enemyTurn())));
                    ArrayList<SkillBehavior> skills = currentPokemon.getSkill();
                    for (int j = 0; j < 4; j++) {
                        skillButtons[j].clear();
                        if (j >= skills.size() || skills.get(j) == null) {
                            skillButtons[j].setDisabled(true);
                            skillButtons[j].add("No Skill");
                            continue;
                        }
                        final int t = j;
                        skillButtons[j].add(skills.get(j).description());
                        skillButtons[j].addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                skills.get(t).update(enemy, currentPokemon, playerPokemons);
                                switchTable.setVisible(false);
                                skillTable.setVisible(false);
                                dialog.setVisible(true);
                                dialog.setDescription(String.format("%s使用了%s", currentPokemon.getName(), skills.get(t).description()));
                                dialog.addAction(Actions.sequence(Actions.delay(2), Actions.hide(), Actions.run(() -> enemyTurn())));
                            }
                        });
                    }
                }
            });
        }
        switchTable.add(pokemonButtons[0]).expandX().fillX();
        switchTable.add(pokemonButtons[1]).expandX().fillX().row();
        switchTable.add(pokemonButtons[2]).expandX().fillX();
        switchTable.add(pokemonButtons[3]).expandX().fillX().row();
        stack.add(switchTable);


        ArrayList<SkillBehavior> skills = currentPokemon.getSkill();
        for (int j = 0; j < 4; j++) {
            skillButtons[j] = new Button(skin);
            if ((j + 1) % 2 == 0)
                skillTable.add(skillButtons[j]).row();
            else
                skillTable.add(skillButtons[j]);
            if (j >= skills.size() || skills.get(j) == null) {
                skillButtons[j].setDisabled(true);
                skillButtons[j].add("No Skill");
                continue;
            }
            final int t = j;
            skillButtons[j].add(skills.get(j).description());
            skillButtons[j].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    skills.get(t).update(enemy, currentPokemon, playerPokemons);
                    switchTable.setVisible(false);
                    skillTable.setVisible(false);
                    dialog.setVisible(true);
                    dialog.setDescription(String.format("%s使用了%s", currentPokemon.getName(), skills.get(t).description()));
                    dialog.addAction(Actions.sequence(Actions.delay(2), Actions.hide(), Actions.run(() -> enemyTurn())));
                }
            });
        }
        skillTable.setVisible(false);
        stack.add(skillTable);

        for (InventoryItem item: gameStatus.playerInventory){
            if (item.getName().equals("回復藥水") || item.getName().equals("超級回復藥水") || item.getName().equals("究極回復藥水")){
                Button b = new Button(skin);
                b.add(item.getName());
                b.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        currentPokemon.setHP(currentPokemon.getHP() + PosionEffectFactory.getEffect(item.getName()));
                        item.consume();
                        if (item.getNum() == 0){
                            b.setDisabled(true);
                            gameStatus.playerInventory.remove(item);
                        }
                        enemyTurn();
                        packageTable.setVisible(false);
                    }
                });
                packageTable.add(b);
            }
        }
        packageTable.setVisible(false);
        stack.add(packageTable);

        Button pokemonButton = new Button(skin);
        pokemonButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchTable.setVisible(true);
                skillTable.setVisible(false);
                packageTable.setVisible(false);
            }
        });
        pokemonButton.add("Pokemon");
        optionTable.add(pokemonButton).expandX().fillX().row();

        Button skillButton = new Button(skin);
        skillButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchTable.setVisible(false);
                skillTable.setVisible(true);
                packageTable.setVisible(false);
            }
        });

        skillButton.add("Skill");
        optionTable.add(skillButton).expandX().fillX().row();

        Button backPack = new Button(skin);
        backPack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchTable.setVisible(false);
                skillTable.setVisible(false);
                packageTable.setVisible(true);
            }
        });
        backPack.add("Backpack");
        optionTable.add(backPack).expandX().fillX().row();

        Window finalWindow = new Window("", skin);
        finalWindow.pad(0);
        finalWindow.add(stack).pad(0);
        finalWindow.setSize(Const.maxViewportWidth - optionTable.getWidth(), optionTable.getHeight());
        finalTable.add(finalWindow).expandX().fillY().fillX().pad(0);
        finalTable.add(optionTable);
        finalTable.left().bottom();

        stage.addActor(finalTable);
    }

    void enemyTurn(){
        if (!enemy.alive()){
            stage.clear();
            resultButton.clear();
            resultButton.add("你贏了");
            stage.addActor(resultButton);
            resultButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameStatus.getGameStatus().addPokemon(enemy.getName());
                    GameStatus.getGameStatus().save();
                    game.setScreen(game.getGameScreen());
                }
            });
            return;
        }
        currentPokemon.beingAttack(Math.max((int)(enemy.getATK() * 0.2f), 1));
        dialog.setVisible(true);
        dialog.setDescription(String.format("%s攻擊", enemy.getName()));
        dialog.addAction(Actions.sequence(Actions.delay(2), Actions.hide()));
        if (!currentPokemon.alive()){
            optionTable.setVisible(false);
            switchTable.addAction(Actions.sequence(Actions.delay(2), Actions.show()));
            for (int i = 0; i < playerPokemons.length; i++){
                if (playerPokemons[i] != null && !playerPokemons[i].alive()) {
                    pokemonButtons[i].addAction(Actions.touchable(Touchable.disabled));
                }
            }
            for (Pokemon playerPokemon : playerPokemons) {
                if (playerPokemon != null && playerPokemon.alive()) {
                    return;
                }
            }
            stage.clear();
            resultButton.clear();
            resultButton.add("你輸了");
            resultButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(game.getGameScreen());
                }
            });
            stage.addActor(resultButton);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        stage.getViewport().apply();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().draw(currentPokemon.texture, 0, 100);
        stage.getBatch().draw(enemy.texture, Const.maxViewportWidth - enemy.texture.getWidth(), 100);
        font.draw(stage.getBatch(), String.format("HP: %d", currentPokemon.getHP()), 0 + currentPokemon.texture.getWidth() / 2f, 100 + currentPokemon.texture.getHeight());
        font.draw(stage.getBatch(), String.format("HP: %d", enemy.getHP()), Const.maxViewportWidth - enemy.texture.getWidth() / 2f, 100 + enemy.texture.getHeight());
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }
    
}
