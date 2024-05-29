package com.simanglam.bosswar;

import com.badlogic.gdx.math.Rectangle;

public class AttackInfo {
    String from;
    Rectangle demageRectangle;
    int damege;
    public AttackInfo(String from, Rectangle dRectangle, int damege){
        this.from = from;
        this.demageRectangle = dRectangle;
        this.damege = damege;
    }
}
