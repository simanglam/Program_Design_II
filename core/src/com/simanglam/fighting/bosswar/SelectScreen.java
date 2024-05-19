package com.simanglam.fighting.bosswar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.simanglam.Main;
import com.simanglam.util.AbstractScreen;
import com.simanglam.util.Const;

public class SelectScreen extends AbstractScreen {

    Main game;
    List<Path> pathList;
    Stage stage;
    ArrayList<String> strings;

    public SelectScreen(final Main game){
        this.game = game;
        Path path = Paths.get(Gdx.files.getLocalStoragePath() + "enemies");
        stage = new Stage(new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight));
        strings = new ArrayList<>();
        try {
            pathList = Files.walk(path)
            .filter(filePath -> !Files.isDirectory(filePath) && filePath.toString().endsWith("/info.json"))
            .collect(Collectors.toList());
            pathList.forEach((file) -> {System.out.println(file);});
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Table t = new Table(skin);
        t.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        pathList.forEach((file) -> {
            int i = 1;
            Button b = new Button(skin);
            ImageButton ib = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal(file.getParent().toString() + "/image/idle-0.png"))));
            b.add(ib);
            Cell<Button> c = t.add(b).center().expandX();
            if (i % 4 == 0){
                i = 0;
                c.row();
            }
            i++;
        });
        Table outterTable = new Table(skin);
        outterTable.setSize(Const.maxViewportWidth, Const.maxViewportHeight);
        outterTable.add(new ScrollPane(t));
        stage.addActor(outterTable);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(this.stage);
    }
    
}


class PokemonInfo {

}