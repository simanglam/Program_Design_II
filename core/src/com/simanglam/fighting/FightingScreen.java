package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simanglam.Main;
import com.simanglam.fighting.skillbehavior.SimpleAttack;
import com.simanglam.fighting.skillbehavior.SkillBehavior;
import com.simanglam.map.ui.Dialog;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

import java.util.ArrayList;

public class FightingScreen extends AbstractScreen {

    Stage stage;
    GameStatus gameStatus;
    Main game;
    Viewport viewport;

    Button[] pokemonButtons;
    Table skillTable;
    Table switchTable;
    Table optionTable;
    Dialog dialog;

    Pokemon enemy;
    Pokemon[] playerPokemons;
    Pokemon currentPokemon;


    public FightingScreen(Main game) {
        this.viewport = new  FitViewport(Const.maxViewportWidth, Const.maxViewportHeight);
        this.stage = new Stage(viewport);
        this.gameStatus = GameStatus.getGameStatus();
        this.game = game;
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json", Skin.class);

        this.enemy = new Pokemon(10, new SimpleAttack(), new SimpleAttack(), new SimpleAttack(), null);
        Table finalTable = new Table();
        skillTable = new Table();
        switchTable = new Table();
        optionTable = new Table(skin);

        optionTable.setSize(Const.maxViewportWidth / 4f, Const.maxViewportHeight / 4f);

        finalTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        Stack stack = new Stack();
        Button[] skillButtons = new Button[4];

        dialog = new Dialog();
        dialog.setVisible(false);
        dialog.setWidth(Const.maxViewportWidth);
        dialog.setFillParent(true);
        stack.add(dialog);
        playerPokemons = new Pokemon[4];
        playerPokemons[0] = new Pokemon(10, new SimpleAttack(), new SimpleAttack(), new SimpleAttack(), null);
        playerPokemons[1] = new Pokemon(10, new SimpleAttack(), new SimpleAttack(), null, null);
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
                    dialog.setDescription("換成了%s".formatted(currentPokemon.getName()));
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
                                dialog.setDescription("%s使用了%s".formatted(currentPokemon.getName(), skills.get(t).description()));
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
                    dialog.setDescription("%s使用了%s".formatted(currentPokemon.getName(), skills.get(t).description()));
                    dialog.addAction(Actions.sequence(Actions.delay(2), Actions.hide(), Actions.run(() -> enemyTurn())));
                }
            });
        }
        skillTable.setVisible(false);
        stack.add(skillTable);

        Button pokemonButton = new Button(skin);
        pokemonButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchTable.setVisible(true);
                skillTable.setVisible(false);
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
        currentPokemon.beingAttack(Math.max((int)(enemy.getATK() * 0.2f), 1));
        System.out.printf("Pokemon: %d, Enemy: %d\n", currentPokemon.getHP(), enemy.getHP());
        dialog.setVisible(true);
        dialog.setDescription("%s攻擊".formatted(enemy.getName()));
        dialog.addAction(Actions.sequence(Actions.delay(2), Actions.hide()));
        if (!currentPokemon.alive()){
            optionTable.setVisible(false);
            switchTable.addAction(Actions.sequence(Actions.delay(2), Actions.show()));
            for (int i = 0; i < playerPokemons.length; i++){
                if (playerPokemons[i] != null && !playerPokemons[i].alive()) {
                    pokemonButtons[i].setDisabled(true);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        stage.getBatch().begin();
        stage.getBatch().draw(currentPokemon.texture, 0, 100);
        stage.getBatch().draw(enemy.texture, Const.maxViewportWidth - enemy.texture.getWidth(), 100);
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getViewport().apply();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }
    
}
