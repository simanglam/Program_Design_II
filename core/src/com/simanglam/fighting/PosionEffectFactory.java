package com.simanglam.fighting;

public class PosionEffectFactory {
    static int getEffect(String name){
        if (name.equals("回復藥水")) return 10;
        if (name.equals("超級回復藥水")) return 30;
        if (name.equals("究極回復藥水")) return 50;
        return 0;
    }
}
