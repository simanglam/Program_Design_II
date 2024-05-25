package com.simanglam.util;

import com.badlogic.gdx.Gdx;

public class InventoryItem {
    private String name, description;
    private int num;

    public static InventoryItem generateInventoryItem(String name){
        return JsonLoaders.normalLoader.fromJson(InventoryItem.class, Gdx.files.internal("items/" + name + "/info.json"));
    }

    public String getName(){
        return name;
    }

    public String description(){
        return description;
    }

    public int getNum(){
        return num;
    }

    public boolean consume(){
        if (num <= 0)
            return false;
        num -= 1;
        return true;
    }

    public boolean add(){
        num += 1;
        return true;
    }
}
