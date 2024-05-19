package com.simanglam.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.simanglam.fighting.Pokemon;

public class GameStatus {
    static GameStatus gameStatus;
    private ArrayList<InventoryItem> playerInventory;
    private ArrayList<Pokemon> pokemons;
    private HashMap<String, Boolean> statusHashMap;


    private GameStatus(){
        playerInventory = new ArrayList<InventoryItem>();
    }

    public static GameStatus getGameStatus(){
        if (gameStatus == null)
            gameStatus = new GameStatus();
        return gameStatus;
    }

    public static void clearGameStatus(){
        GameStatus.gameStatus = null;
    }
}
