package com.simanglam.fighting;

import com.simanglam.fighting.skillbehavior.SimpleAttack;

public class PokemonFactory {
    public static Pokemon buildPokemon(String name){
        if (name.equals("test"))
            return new Pokemon(10, 20, name, "booboo.png", new SimpleAttack(), new SimpleAttack(), null, null);
        return new Pokemon(10, 20, name, "booboo.png", new SimpleAttack(), new SimpleAttack(), null, null);
    }
}
