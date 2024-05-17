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
        return null; // or throw an exception if the pokemon is not found
    }

    private SpriteBatch batch;
    private Texture map;
    private Texture currentPokemonTexture;
    private Texture enemie;
    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private String currentPokemonName;

    private enum ButtonType {
        MAIN,
        SKILL,
        POKEMON,
        BACKPACK
    }

    public PokemonScreen(final Main game) {
        batch = new SpriteBatch();
        map = new Texture("main.png");
        enemie = new Texture("enemies/base/image/smallfiredragon.png");
        currentPokemonTexture = new Texture(pokemonList.pokemons.get(0).image); // Use the image of the first Pokemon
        currentPokemonName = pokemonList.pokemons.get(0).name; // Or any default Pokémon name you desire

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        font = new BitmapFont();
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
                                    // No action needed
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
            final String skillName = skills.get(i).name;
            listeners[i] = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(skillName + " button clicked!");
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

    private ClickListener[] createPokemonButtonListeners(final Main game) {
        ClickListener[] listeners = new ClickListener[pokemonList.pokemons.size() + 1];
        for (int i = 0; i < pokemonList.pokemons.size(); i++) {
            final Pokemon pokemon = pokemonList.pokemons.get(i);
            listeners[i] = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentPokemonTexture.dispose(); // Dispose the old texture
                    currentPokemonTexture = new Texture(pokemon.image);
                    currentPokemonName = pokemon.name; // Update the currentPokemonName
                    System.out.println(pokemon.name + " selected!");
                    initializeButtons(game, ButtonType.SKILL); // Reinitialize buttons to show skills for the selected Pokemon
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(map, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(currentPokemonTexture, 50, 60);

        // Draw enemy image with reduced size
        float enemyReducedWidth = enemie.getWidth() / 8.0f;
        float enemyReducedHeight = enemie.getHeight() / 8.0f;
        batch.draw(enemie, 400, 60, enemyReducedWidth, enemyReducedHeight);

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
        enemie.dispose();
        stage.dispose();
        font.dispose();
    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }
}
