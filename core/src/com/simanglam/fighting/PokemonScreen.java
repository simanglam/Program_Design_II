package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;

public class PokemonScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Texture map;
    private Texture pokemon;
    private Stage stage;
    private BitmapFont font;
    private Skin skin;

    private enum ButtonType {
        MAIN,
        SKILL,
        POKEMON,
        BACKPACK
    }

    public PokemonScreen(final Main game) {
        batch = new SpriteBatch();
        map = new Texture("bosswar.png");
        pokemon = new Texture("enemies/base/image/idle-0.png");
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        initializeButtons(game, ButtonType.MAIN);
        handleInput();
    }

    private void initializeButtons(final Main game, ButtonType type) {
        stage.clear();

        String[] buttonLabels;
        ClickListener[] buttonListeners;

        switch (type) {
            case MAIN:
                buttonLabels = new String[]{"Backpack", "Skill", "Pokemon", "Escape"};
                buttonListeners = new ClickListener[]{
                        new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                initializeButtons(game, ButtonType.BACKPACK);
                            }
                        },
                        new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                initializeButtons(game, ButtonType.SKILL);
                            }
                        },
                        new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                initializeButtons(game, ButtonType.POKEMON);
                            }
                        },
                        new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                game.setScreen(game.getGameScreen());
                            }
                        }
                };
                break;
            case SKILL:
                buttonLabels = new String[]{"Skill 1", "Skill 2", "Skill 3", "Skill 4", "Back"};
                buttonListeners = createButtonListeners(game, "Skill");
                break;
            case POKEMON:
                buttonLabels = new String[]{"Pokemon 1", "Pokemon 2", "Pokemon 3", "Pokemon 4", "Back"};
                buttonListeners = createButtonListeners(game, "GO Pokemon");
                break;
            case BACKPACK:
                buttonLabels = new String[]{"Tool 1", "Tool 2", "Tool 3", "Tool 4", "Back"};
                buttonListeners = createButtonListeners(game, "Tool");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        addButtonsToStage(buttonLabels, buttonListeners);
    }

    private ClickListener[] createButtonListeners(final Main game, String messagePrefix) {
        return new ClickListener[]{
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(messagePrefix + " 1 button clicked!");
                    }
                },
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(messagePrefix + " 2 button clicked!");
                    }
                },
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(messagePrefix + " 3 button clicked!");
                    }
                },
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(messagePrefix + " 4 button clicked!");
                    }
                },
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        initializeButtons(game, ButtonType.MAIN);
                    }
                }
        };
    }

    private void addButtonsToStage(String[] labels, ClickListener[] listeners) {
        float buttonWidth = 200f;
        float buttonHeight = 60f;
        float buttonX = 10f;
        float buttonY = Gdx.graphics.getHeight() - buttonHeight - 10f;

        for (int i = 0; i < labels.length; i++) {
            TextButton button = new TextButton(labels[i], skin);
            button.setBounds(buttonX + (i % 2) * (buttonWidth + 10f), buttonY - (i / 2) * (buttonHeight + 10f), buttonWidth, buttonHeight);
            button.addListener(listeners[i]);
            stage.addActor(button);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(map, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(pokemon, 50, 120);
        batch.draw(pokemon, 600, 120);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        stage.dispose();
        font.dispose();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }
}
