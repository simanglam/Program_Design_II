package com.simanglam.fighting.bosswar;

class EnemySpawnInfo {
    BossWarActor enemy;
    int spawnCoolDown;

    public EnemySpawnInfo(BossWarActor proto, int coolDown) {
        this.enemy = proto;
        spawnCoolDown = coolDown;
    }
    
}
