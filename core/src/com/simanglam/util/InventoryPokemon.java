package com.simanglam.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.simanglam.fighting.Pokemon;
import com.simanglam.bosswar.BossWarActor;

public class InventoryPokemon {
    String name, description;
    
    public BossWarActor generateBossWarActor(){
        return new BossWarActor("enemies/" + name + "/bosswar-info.json", false);
    }

    public Pokemon generatePokemon(){
        return new Json().fromJson(Pokemon.class, Gdx.files.internal("enemies/base/fighting-info.json"));
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
}
