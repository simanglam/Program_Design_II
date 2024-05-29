package com.simanglam.fighting;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.simanglam.fighting.skillbehavior.SkillBehavior;

public class Pokemon {
    String name;
    private int healthPoint;
    private int atk;
    private float defanceRate;
    private int defanceCount;
    private ArrayList<SkillBehavior> skills;
    public Texture texture;

    public Pokemon(int healthPoint, SkillBehavior a1, SkillBehavior a2, SkillBehavior a3, SkillBehavior a4) {
        this.healthPoint = healthPoint;
        texture = new Texture("booboo.png");
        skills = new ArrayList<>();
        skills.add(a1);
        skills.add(a2);
        skills.add(a3);
        skills.add(a4);
        atk = 20;
        name = "我不知道";
    }

    public void beingAttack(int atk){
        if (defanceCount > 0){
            --defanceCount;
            atk *= defanceRate;
        }
        healthPoint -= atk;
    }

    public int getHP(){
        return healthPoint;
    }

    public String getName(){
        return name;
    }

    public void setHP(int hp){
        healthPoint = hp;
    }

    public boolean alive(){
        return healthPoint > 0;
    }

    public ArrayList<SkillBehavior> getSkill(){
        return skills;
    }

    public int getATK(){
        return atk;
    }

}
