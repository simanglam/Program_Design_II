package com.simanglam.util;

import com.badlogic.gdx.assets.AssetManager;

public class AssetsManagerWrapper {
    static AssetsManagerWrapper a;
    public AssetManager assetManager;

    private AssetsManagerWrapper(){
        assetManager = new AssetManager();
    }

    public static synchronized AssetsManagerWrapper getAssetsManagerWrapper(){
        if (a == null)
            a = new AssetsManagerWrapper();
        return a;
    }
}
