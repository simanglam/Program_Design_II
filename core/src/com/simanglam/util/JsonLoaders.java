package com.simanglam.util;

import com.badlogic.gdx.utils.Json;
import com.simanglam.fighting.bosswar.BossWarInfo;
import com.simanglam.fighting.bosswar.SpawnInfo;

public class JsonLoaders {
    public static Json normalLoader;
    public static Json BossWarInfoLoader;
    static {
        JsonLoaders.normalLoader = new Json();
        JsonLoaders.BossWarInfoLoader = new Json();

        JsonLoaders.BossWarInfoLoader.setElementType(BossWarInfo.class, "onstage", SpawnInfo.class);
        JsonLoaders.BossWarInfoLoader.setElementType(BossWarInfo.class, "enemies", SpawnInfo.class);

    }
}
