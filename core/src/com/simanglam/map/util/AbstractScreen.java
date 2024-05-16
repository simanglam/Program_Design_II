package com.simanglam.util;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;

public abstract class AbstractScreen extends InputAdapter implements Screen {
    public abstract void handleInput();
    public void pause(){};
    public void show(){};
    public void hide(){};
    public void resume(){};
    public void dispose(){};
}
