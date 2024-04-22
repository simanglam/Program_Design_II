package com.simanglam.fighting;

import com.simanglam.fighting.bosswar.PokemonWarrior;

public interface Pokemon {
    public String getName();
    public String getDescription();
    public String getInfo();
    public PokemonWarrior generatePokemonWarrior();
}
