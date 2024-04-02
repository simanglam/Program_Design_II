package com.simanglam;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class Dialog extends Window {
    static float a = 0.04f;
    ArrayList<String> descriptions;
    int current;
    int cursor;
    float deltaT;
    BitmapFont font;
    Stage stage;
    boolean show;
    Label label;
    public Dialog(final Stage stage){
        super("Dialog", new Skin(Gdx.files.internal("data/uiskin.json")));
        this.stage = stage;
        deltaT = 0.0f;
        this.descriptions = new ArrayList<String>();
        descriptions.add("A");
        cursor = 1;
        current = 0;
        label = new Label(descriptions.get(current) , getSkin());
        setPosition(0, 120);
        setColor(new Color(255, 255, 255, 255));
        setWidth(stage.getWidth());
        setHeight(stage.getHeight() / 5);
        this.add(label);
        this.addListener(new DialogListener(this));
    }

    public void act(float deltaT){
        this.deltaT += deltaT;
        if (this.deltaT > a){
            cursor = (cursor++ >= descriptions.get(current).length()) ? descriptions.get(current).length() : cursor;
            label.setText(descriptions.get(current).substring(0, cursor));
            this.deltaT -= a;
        }
    }

    public void draw(Batch batch){
        font.draw(batch, descriptions.get(current).substring(0, cursor), 0, 0);
    }

    public void setDescription(String description){
        this.descriptions.clear();
        String[] descriptions = description.split("//");
        for (int i = 0; i < descriptions.length; i++)
            this.descriptions.add(descriptions[i]);
        cursor = 1;
        current = 0;
    }

    public boolean hasNextDescription(){
        return current < this.descriptions.size() - 1;
    }

    public void nextDescription(){
        current += 1;
        cursor = 1;
        label.setText(this.descriptions.get(current).substring(0, cursor));
    }
}


class DialogListener extends InputListener {
    Dialog dialog;
    public DialogListener(final Dialog dialog){
        this.dialog = dialog;
    }
    @Override
    public boolean keyDown(InputEvent event, int keyCode){
        Dialog a = (Dialog)event.getListenerActor();
        if (a.hasNextDescription()){
            a.nextDescription();
            return true;
        }
        else
            a.remove();
        return false;
    }
}