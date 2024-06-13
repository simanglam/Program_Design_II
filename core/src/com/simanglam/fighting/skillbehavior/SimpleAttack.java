package com.simanglam.fighting.skillbehavior;

import com.simanglam.fighting.Pokemon;

public class SimpleAttack implements SkillBehavior {
    @Override
    public String description() {
        return "測試用攻擊";
    }

    @Override
    public void update(Pokemon enemy, Pokemon self, Pokemon[] teams){
        enemy.beingAttack((int)(self.getATK() * 0.4f));
    } 
}
