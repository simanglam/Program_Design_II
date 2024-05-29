package com.simanglam.fighting.skillbehavior;

import java.util.ArrayList;

import com.simanglam.fighting.Pokemon;

public interface SkillBehavior {
    public String description();
    public void update(Pokemon enemy, Pokemon self, Pokemon[] teams);
}
