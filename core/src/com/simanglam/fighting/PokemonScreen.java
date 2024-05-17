package com.simanglam.fighting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.badlogic.gdx.utils.Json;
import com.simanglam.fighting.Pokemon;
import com.simanglam.fighting.Skill;

import java.util.List;

class PokemonList {
    public List<Pokemon> pokemons;
}

public class PokemonScreen extends AbstractScreen {
    private static PokemonList pokemonList;

    static {
        Json json = new Json();
        pokemonList = json.fromJson(PokemonList.class, Gdx.files.internal("enemies/base/fighting-info.json"));
    }

    public static List<Skill> getSkillsByPokemonName(String pokemonName) {
        for (Pokemon pokemon : pokemonList.pokemons) {
            if (pokemon.name.equals(pokemonName)) {
                return pokemon.skills;
            }
        }
        return null;
    }

    private SpriteBatch batch;
    private Texture map;
    private Texture currentPokemonTexture;
    private Texture enemyTexture;
    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private String currentPokemonName;
    private int currentPokemonHealth;
    private int enemyHealth;
    private Main game;

    private enum ButtonType {
        MAIN,
        SKILL,
        POKEMON,
        BACKPACK
    }


    public PokemonScreen(final Main game) {
        this.game = game;
        batch = new SpriteBatch();
        map = new Texture("FTbg.jpg");
        enemyTexture = new Texture("enemies/base/image/smallfiredragon.png");
        currentPokemonTexture = new Texture(pokemonList.pokemons.get(0).image);
        currentPokemonName = pokemonList.pokemons.get(0).name;
        currentPokemonHealth = pokemonList.pokemons.get(0).health;
        enemyHealth = 1000;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font/pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        font = generator.generateFont(parameter);
        generator.dispose();

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        initializeButtons(game, ButtonType.MAIN);
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
                List<Skill> skills = getSkillsByPokemonName(currentPokemonName);
                if (skills != null) {
                    buttonLabels = new String[skills.size() + 1];
                    for (int i = 0; i < skills.size(); i++) {
                        buttonLabels[i] = skills.get(i).name;
                    }
                    buttonLabels[skills.size()] = "Back";
                    buttonListeners = createDynamicButtonListeners(game, skills);
                } else {
                    buttonLabels = new String[]{"No Skills Available", "Back"};
                    buttonListeners = new ClickListener[]{
                            new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    initializeButtons(game, ButtonType.MAIN);
                                }
                            }
                    };
                }
                break;

            case POKEMON:
                buttonLabels = new String[pokemonList.pokemons.size() + 1];
                for (int i = 0; i < pokemonList.pokemons.size(); i++) {
                    buttonLabels[i] = pokemonList.pokemons.get(i).name;
                }
                buttonLabels[pokemonList.pokemons.size()] = "Back";
                buttonListeners = createPokemonButtonListeners(game);
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

    private ClickListener[] createDynamicButtonListeners(final Main game, List<Skill> skills) {
        ClickListener[] listeners = new ClickListener[skills.size() + 1];
        for (int i = 0; i < skills.size(); i++) {
            final Skill skill = skills.get(i);
            listeners[i] = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int damage = calculateDamage(skill);
                    enemyHealth -= damage;
                    System.out.println(currentPokemonName + " used " + skill.name + " and dealt " + damage + " damage to the enemy!");

                    if (enemyHealth <= 0) {
                        gameWon = true;

                        stage.clear();

                        batch.begin();
                        font.draw(batch, "Victory!", 180,180);
                        batch.end();

                        backButton = new TextButton("Back", skin);
                        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 +100, 200, 60);
                        backButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                game.setScreen(game.getGameScreen());
                            }
                        });
                        stage.addActor(backButton);
                    }
                }
            };
        }
        listeners[skills.size()] = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                initializeButtons(game, ButtonType.MAIN);
            }
        };
        return listeners;
    }

    private int calculateDamage(Skill skill) {
        return skill.power;
    }

    private ClickListener[] createPokemonButtonListeners(final Main game) {
        ClickListener[] listeners = new ClickListener[pokemonList.pokemons.size() + 1];
        for (int i = 0; i < pokemonList.pokemons.size(); i++) {
            final Pokemon pokemon = pokemonList.pokemons.get(i);
            listeners[i] = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentPokemonTexture.dispose();
                    currentPokemonTexture = new Texture(pokemon.image);
                    currentPokemonName = pokemon.name;
                    currentPokemonHealth = pokemon.health;
                    System.out.println(pokemon.name + " selected!");
                    initializeButtons(game, ButtonType.SKILL);
                }
            };
        }
        listeners[pokemonList.pokemons.size()] = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                initializeButtons(game, ButtonType.MAIN);
            }
        };
        return listeners;
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

    private boolean gameWon = false;
    private TextButton backButton;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(map, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(currentPokemonTexture, 50, 110);

        float enemyReducedWidth = enemyTexture.getWidth() / 8.0f;
        float enemyReducedHeight = enemyTexture.getHeight() / 8.0f;
        batch.draw(enemyTexture, 400, 110, enemyReducedWidth, enemyReducedHeight);

        font.draw(batch, "HP" + currentPokemonHealth, 70, 70);
        font.draw(batch, "HP" + enemyHealth, 400, 70);

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
        currentPokemonTexture.dispose();
        enemyTexture.dispose();
        stage.dispose();
        font.dispose();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }
}

