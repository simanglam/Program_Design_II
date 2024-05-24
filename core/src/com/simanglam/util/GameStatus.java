package com.simanglam.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;


public class GameStatus {
    static GameStatus gameStatus;
    ArrayList<InventoryItem> playerInventory;
    public final ArrayList<InventoryPokemon> playerInventoryPokemons;
    public final ArrayList<InventoryPokemon> selectedPokemon;
    public int money;
    public final HashMap<String, Boolean> statusHashMap;


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
        this.money = 1000;
    }

    public HashMap<String, Boolean> getStatusHashMap(){
        return statusHashMap;
    }

    public void addItem(String name){
        for (InventoryItem i: playerInventory) {
            if (i.getName().equals(name)){
                i.add();
                return ;
            }
        }
        InventoryItem i = InventoryItem.generateInventoryItem(name);
        i.add();
        playerInventory.add(i);
    }

    /*
    public void consumeItem(String name){
        for (int i = 0; i < playerInventory.size(); i++){
            playerInventory.get(i)
        }
        playerInventory.add(i);
    }
    */

    public void save(){
        if (gameStatus == null) return;
        String save = new Json().prettyPrint(this);
        System.out.println(this.money);

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
