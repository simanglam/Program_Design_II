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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.MathUtils;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.map.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.Actor;




import java.util.List;
import java.util.Arrays;

class PokemonList {
    public List<Pokemon> pokemons;
}

class Enemy {
    public String name;
    public String image;
    public int health;
    public List<Skill> skills;
}

public class PokemonScreen extends AbstractScreen {
    private BattleState currentState;
    private static PokemonList pokemonList;
    private static Enemy enemy;
    private boolean[] pokemonAlive;


    static {
        Json json = new Json();
        pokemonList = json.fromJson(PokemonList.class, Gdx.files.internal("enemies/base/fighting-info.json"));
        enemy = json.fromJson(Enemy.class, Gdx.files.internal("enemies/base/enemy-info.json"));
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
    private boolean gameWon;

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
        enemyTexture = new Texture(enemy.image);
        currentPokemonTexture = new Texture(pokemonList.pokemons.get(0).image);
        currentPokemonName = pokemonList.pokemons.get(0).name;
        currentPokemonHealth = pokemonList.pokemons.get(0).health;
        enemyHealth = enemy.health;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font/pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        font = generator.generateFont(parameter);
        generator.dispose();

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        initializeButtons(game, ButtonType.MAIN);

        currentState = BattleState.PLAYER_TURN;
        gameWon = false;
        pokemonAlive = new boolean[pokemonList.pokemons.size()];
        Arrays.fill(pokemonAlive, true);
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
                        new ClickListener(){
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
            
                // 檢查已死亡的寶可夢並禁用按鈕
                for (int i = 0; i < pokemonList.pokemons.size(); i++) {
                    if (!pokemonAlive[i]) {
                        stage.getActors().get(i).setTouchable(Touchable.disabled);
                    }
                }
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
                    hideButtons();
                    showSkillDialog();
                    int damage = calculateDamage(skill);
                    enemyHealth -= damage;
                    System.out.println(currentPokemonName + " used " + skill.name + " and dealt " + damage + " damage to the enemy!");

                    if (enemyHealth <= 0) {
                        currentState = BattleState.VICTORY;
                        gameWon = true;
                        System.out.println("Victory!");
                        // 顯示勝利畫面或返回主畫面
                        stage.clear();

                        batch.begin();
                        font.draw(batch, "Victory!", 180, 180);
                        batch.end();

                        TextButton backButton = new TextButton("Back", skin);
                        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 100, 200, 60);
                        backButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                game.setScreen(game.getGameScreen());
                            }
                        });
                        stage.addActor(backButton);
                    } else {
                        // 玩家攻擊結束後，切換狀態到敵人回合並且啟動敵人攻擊
                        currentState = BattleState.ENEMY_TURN;
                        delayedEnemyAction(1.5f); // 調用 delayedEnemyAction() 方法，1.0 秒後觸發敵人的攻擊行動
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

    for (int j = 0; j < pokemonList.pokemons.size(); j++) {
        final Pokemon pokemon = pokemonList.pokemons.get(i);
        if (pokemonAlive[j]) {
            listeners[j] = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentPokemonName = pokemon.name;
                    currentPokemonHealth = pokemon.health;
                    currentPokemonTexture = new Texture(pokemon.image);
                    System.out.println("Switched to " + currentPokemonName);
                    initializeButtons(game, ButtonType.MAIN);
                }
            };
        } else {

            listeners[j] = null;
        }
    }
    

    private ClickListener[] createButtonListeners(final Main game, String buttonType) {
        ClickListener[] listeners = new ClickListener[5];
        for (int i = 0; i < 4; i++) {
            final String tool = buttonType + " " + (i + 1);
            listeners[i] = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(tool + " selected.");
                    initializeButtons(game, ButtonType.MAIN);
                }
            };
        }
        listeners[4] = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                initializeButtons(game, ButtonType.MAIN);
            }
        };
        return listeners;
    }

    private void addButtonsToStage(String[] labels, ClickListener[] listeners) {
        float buttonWidth = 200f;
        float buttonHeight = 60f;
        float startX = 20f;
        float startY = Gdx.graphics.getHeight() - 80f;
        float spacingX = 10f;
        float spacingY = 10f;
    
        int buttonsPerRow = 2; 
    
        for (int i = 0; i < labels.length; i++) {
            float row = i / buttonsPerRow; 
            float col = i % buttonsPerRow; 
    
            float buttonX = startX + col * (buttonWidth + spacingX);
            float buttonY = startY - row * (buttonHeight + spacingY);
    
            TextButton button = new TextButton(labels[i], skin);
            button.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
            button.addListener(listeners[i]);
            stage.addActor(button);
        }
    }



    private void showSkillDialog() {
        Dialog dialog = new Dialog(stage);
        dialog.setDescription("Your Pokemon used a skill!");
        dialog.setPosition(Gdx.graphics.getWidth() / 2 - dialog.getWidth() / 2, Gdx.graphics.getHeight() - dialog.getHeight() - 20); // 设置对话框的位置为屏幕顶部
        stage.addActor(dialog);
        
       
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dialog.setVisible(false); 
            }
        }, 2.0f);
    }
    
    private void showEnemySkillDialog() {
        Dialog dialog = new Dialog(stage);
        dialog.setDescription("Enemy Pokemon used a skill!");
        dialog.setPosition(Gdx.graphics.getWidth() / 2 - dialog.getWidth() / 2, Gdx.graphics.getHeight() - dialog.getHeight() - 20); // 设置对话框的位置为屏幕顶部
        stage.addActor(dialog);
        
        
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dialog.setVisible(false); 
                showButtons(); 
            }
        }, 2.0f);
    }
    
    
    private void hideButtons() {
        for (Actor actor : stage.getActors()) {
            if (actor instanceof TextButton) {
            actor.setVisible(false);
            }
        }
    }


    private void showButtons() {  
        for (Actor actor : stage.getActors()) {
            if (actor instanceof TextButton) {
                actor.setVisible(true);
            }
        }
    }


    private void delayedEnemyAction(float delay) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                enemyTurn();
            }
        }, delay);
    }

    private void enemyTurn() {
        if (currentState == BattleState.ENEMY_TURN && !gameWon && enemyHealth > 0) {
            int randomSkillIndex = MathUtils.random(enemy.skills.size() - 1);
            Skill randomSkill = enemy.skills.get(randomSkillIndex);
            int damage = randomSkill.power;
            currentPokemonHealth -= damage;
            showEnemySkillDialog();
            System.out.println("Enemy used " + randomSkill.name + " and dealt " + damage + " damage!");
    
            if (currentPokemonHealth <= 0) {
                // 更新寶可夢的死亡狀態
                pokemonAlive[currentPokemonIndex] = false;
    
                // 檢查是否還有活著的寶可夢
                boolean anyPokemonAlive = false;
                for (boolean alive : pokemonAlive) {
                    if (alive) {
                        anyPokemonAlive = true;
                        break;
                    }
                }
    
                if (!anyPokemonAlive) {
                    currentState = BattleState.DEFEAT;
                    gameWon = false;
                    System.out.println("Defeat!");
                    // 顯示失敗畫面或返回主畫面
                    stage.clear();
    
                    batch.begin();
                    font.draw(batch, "Defeat!", 180, 180);
                    batch.end();
    
                    TextButton backButton = new TextButton("Back", skin);
                    backButton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 100, 200, 60);
                    backButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            game.setScreen(game.getGameScreen());
                        }
                    });
                    stage.addActor(backButton);
                } else {
                    // 切換回玩家回合
                    currentState = BattleState.PLAYER_TURN;
                }
            }
        }
    }
    

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(map, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        skin.dispose();
    }
}
