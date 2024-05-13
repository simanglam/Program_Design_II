package com.simanglam.util;

import java.util.ArrayList;

import com.simanglam.fighting.Pokemon;

public class GameStatus {
    private static GameStatus gameStatus;
    private ArrayList<InventoryItem> playerInventory;

    private GameStatus() {
        playerInventory = new ArrayList<InventoryItem>();
    }

    public static GameStatus getInstance() {
        if (gameStatus == null) {
            gameStatus = new GameStatus();
        }
        return gameStatus;
    }

    public void clearGameStatus() {
        playerInventory.clear();
        gameStatus = null;
    }

    // Add more methods as needed

    // Example method to add an item to the player's inventory
    public void addItemToInventory(InventoryItem item) {
        playerInventory.add(item);
    }

    // Example method to get the player's inventory
    public ArrayList<InventoryItem> getPlayerInventory() {
        return playerInventory;
    }
}
