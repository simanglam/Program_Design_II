package com.simanglam.map.ui;

import java.util.Arrays;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.simanglam.map.MapScreen;
import com.simanglam.util.AssetsManagerWrapper;
import com.simanglam.util.GameStatus;

public class PuzzleDialog extends Window {

    Label[] labels;
    int[] values;
    int[] answer;
    int cursor;

    public PuzzleDialog(MapScreen screen, String password, String reward, String word) {
        super("", (Skin)AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json"));
        Skin skin = AssetsManagerWrapper.getAssetsManagerWrapper().assetManager.get("data/uiskin.json");
        labels = new Label[4];
        values = new int[4];
        answer = new int[4];

        for (int i = 0; i < 4; i++) {
            values[i] = 0;
            answer[i] = password.charAt(i) - '0';
            labels[i] = new Label("0", skin);
        }
        this.add(labels);
        cursor = 0;
        PuzzleDialog p = this;
        labels[cursor].addAction(Actions.forever(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f))));

        this.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode){
                if (keycode != Keys.A && keycode != Keys.S && keycode != Keys.D && keycode != Keys.W && keycode != Keys.ENTER)
                    return false;
                if (keycode == Keys.A || keycode == Keys.D){
                    labels[cursor].clearActions();
                    labels[cursor].setColor(1, 1, 1, 1);
                    if (keycode == Keys.A) 
                        cursor = (cursor - 1 >= 0) ? cursor - 1 : 3;
                    if (keycode == Keys.D)
                        cursor = (cursor + 1 < 4) ? cursor + 1 : 0;
                    labels[cursor].addAction(Actions.forever(Actions.sequence(Actions.alpha(1, 0.5f), Actions.alpha(0, 0.5f))));
                }
                if (keycode == Keys.W || keycode == Keys.S){
                    if (keycode == Keys.W)
                        values[cursor] = (values[cursor] + 1) % 10;
                    if (keycode == Keys.S)
                        values[cursor] = (values[cursor] - 1 >= 0) ? values[cursor] - 1 : 9;
                    labels[cursor].setText(values[cursor]);
                }
                if (keycode == Keys.ENTER){
                    p.addAction(Actions.removeActor());
                    System.out.println(values[0]);
                    System.out.println(answer[0]);
                    if (Arrays.equals(values, answer)){
                        screen.addDialog(word);
                        GameStatus.getGameStatus().addItem(reward);
                    }
                    else
                        screen.addDialog("失敗");
                }
                return true;
            }
        });
    }
}
