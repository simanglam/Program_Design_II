package com.simanglam.fighting.bosswar.attackBehavior;

import com.simanglam.fighting.bosswar.AttackInfo;
import com.simanglam.fighting.bosswar.BossWarActor;

public interface AttackBehavior {
    public AttackInfo generateAttackInfo(BossWarActor bossWarActor);
}
