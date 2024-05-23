package com.simanglam.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;


public class GameStatus {
    static GameStatus gameStatus;
    ArrayList<InventoryItem> playerInventory;
    public ArrayList<InventoryPokemon> playerInventoryPokemons;
    public ArrayList<InventoryPokemon> selectedPokemon;
    HashMap<String, Boolean> statusHashMap;


    private GameStatus(){
        playerInventory = new ArrayList<>();
        playerInventoryPokemons = new ArrayList<>();
        selectedPokemon = new ArrayList<>();
        InventoryPokemon i = new InventoryPokemon();
        i.name = "base";
        i.description = "Just for test";
        InventoryPokemon i2 = new InventoryPokemon();
        i2.name = "base";
        i2.description = "Just for test";
        playerInventoryPokemons.add(i);
        playerInventoryPokemons.add(i2);
        statusHashMap = new HashMap<>();
    }

    public void addItem(InventoryItem inventoryItem){
        if (playerInventory.contains(inventoryItem)){

        }
        else {
            playerInventory.add(inventoryItem);
        }
    }

    public void save(){
        String save = new Json().toJson(this);

        Gdx.files.local("save.txt").writeString(save, false);

    }

    public static synchronized GameStatus getGameStatus(){
        if (gameStatus == null)
            gameStatus = new GameStatus();
        return gameStatus;
    }

    public static void clearGameStatus(){
        GameStatus.gameStatus = null;
    }
}
