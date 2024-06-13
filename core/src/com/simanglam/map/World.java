package com.simanglam.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.simanglam.bosswar.BossWarScreen;
import com.simanglam.fighting.FightingScreen;
import com.simanglam.util.Const;
import com.simanglam.util.GameStatus;

public class World {
    public TiledMap tiledMap;
    TmxMapLoader mapLoader;
    TiledMapRenderer renderer;
    public OrthographicCamera camera;
    public Player player;
    public Viewport viewport;
    float accu;
    double ecounterPossibility;

    public World(){
        this.tiledMap = new TmxMapLoader().load(GameStatus.getGameStatus().currentMap);
        mapLoader = new TmxMapLoader();
        this.renderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.camera = new OrthographicCamera();
        this.player = new Player();
        this.viewport = new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, camera);
        camera.position.set(player.getPosition(), 0);
    }

    public void setView(OrthographicCamera camera){
        this.renderer.setView(camera);
    }

    public void setMap(String map){
        tiledMap.dispose();
        tiledMap = mapLoader.load(map);
        this.renderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void update(float deltaT, MapScreen screen){
        if (ecounterUpdate(deltaT))
            screen.game.setScreen(new FightingScreen(screen.game));
        MapObject currentCollide = getCollideObject(player.getRectangle(), "其他");
        if (currentCollide != null){
            if (currentCollide.getProperties().get("bosswar") != null && !screen.gameStatus.selectedPokemon.isEmpty() && screen.gameStatus.getStatusHashMap().get((String)currentCollide.getProperties().get("bosswar")) == null)
                screen.game.setScreen(new BossWarScreen(screen.game, (String)currentCollide.getProperties().get("bosswar")));
            else if(currentCollide.getProperties().get("bosswar") != null && screen.gameStatus.selectedPokemon.isEmpty()){
                screen.addDialog("You Must select pokemon to continue");
                player.updateX(deltaT);
                playerCollideUpdate(true, "其他");

                player.updateY(deltaT);
                playerCollideUpdate(false, "其他");
                player.freeze();
            }
        }
        player.updateX(deltaT);
        playerCollideUpdate(true, "物件層 1");
        player.updateY(deltaT);
        playerCollideUpdate(false, "物件層 1");
        float[] tempView = {0, 0, 0};
        tempView[0] = Math.max(Math.min(player.getPosition().x, ((int)tiledMap.getProperties().get("width") * (int)tiledMap.getProperties().get("tilewidth")) - (this.viewport.getWorldWidth() / 2)), 0 + (this.viewport.getWorldWidth() / 2));
        tempView[1] = Math.max(Math.min(player.getPosition().y, ((int)tiledMap.getProperties().get("height") * (int)tiledMap.getProperties().get("tileheight")) - (this.viewport.getWorldHeight() / 2)), 0 + (this.viewport.getWorldHeight() / 2));
        this.camera.position.set(tempView);
        this.camera.update();
    }

    public RectangleMapObject getCollideObject(Rectangle rectangle, String layer){
        MapLayer collisionObjectLayer = this.tiledMap.getLayers().get(layer);
        if (collisionObjectLayer == null) return null;
        MapObjects objects = collisionObjectLayer.getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle mapRectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(mapRectangle, rectangle)) {
                return rectangleObject;
            }
        }
        return null;
    }

    public MapObjects getCollideObjects(Rectangle rectangle, String layer){
        MapLayer collisionObjectLayer = this.tiledMap.getLayers().get(layer);
        if (collisionObjectLayer == null) return null;
        MapObjects objects = collisionObjectLayer.getObjects();
        MapObjects collideObjects = new MapObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle mapRectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(mapRectangle, rectangle)) {
                collideObjects.add(rectangleObject);
            }
        }
        return collideObjects;
    }

    public void playerCollideUpdate(boolean x, String layer){
        Rectangle playerRectangle = player.getRectangle();
        MapObjects rectangleMapObjects = getCollideObjects(playerRectangle, layer);
        for (RectangleMapObject rObject : rectangleMapObjects.getByType(RectangleMapObject.class)){
            if (rObject.getProperties().get("portal") != null){
                setMap((String)rObject.getProperties().get("next"));
                GameStatus.getGameStatus().currentMap = (String)rObject.getProperties().get("next");
                for (RectangleMapObject rectangleObject : tiledMap.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
                    if (rectangleObject.getProperties().get("portal") != null && ((String)rectangleObject.getProperties().get("entry")).equals((String)rObject.getProperties().get("exit"))){

                        player.setPosition(rectangleObject.getRectangle().x - (float)rectangleObject.getProperties().get("entryX"), rectangleObject.getRectangle().y - (float)rectangleObject.getProperties().get("entryY"));
                        GameStatus.getGameStatus().currentPosition.set(playerRectangle);
                        camera.position.set(player.rectangle.x, playerRectangle.y, 0);
                        return ;
                    }
                }
            }
            Rectangle rectangle = rObject.getRectangle();
            if (x){
                if (player.isHeadLeft()){
                    player.setPosition(rectangle.x + rectangle.getWidth() + 1, playerRectangle.y);
                }
                else if (player.isHeadRight()){
                    player.setPosition(rectangle.x - playerRectangle.getWidth() - 1, playerRectangle.y);
                }
            }
            else{
                if (player.isHeadDown()){
                    player.setPosition(playerRectangle.x, rectangle.y + rectangle.getHeight() + 1);
                }
                else if (player.isHeadUP()){
                    player.setPosition(playerRectangle.x, rectangle.y - playerRectangle.getHeight() - 1);
                }
            }
        }
    }

    public boolean ecounterUpdate(float deltaT){
        if (getCollideObject(player.getRectangle(), "生怪區") == null){
            accu = 0;
            ecounterPossibility = 0;
            return false;
        }
        accu += deltaT;
        if ((int)accu >= 1){
            accu -= 1;
            ecounterPossibility += 1.0 / (int)((Math.random() * 5) + 1);
            System.out.println(ecounterPossibility);
            if (ecounterPossibility >= (int)(Math.random() * 50) + 1 && !GameStatus.getGameStatus().selectedPokemon.isEmpty()){
                ecounterPossibility = 0;
                player.freeze();
                return true;
            }
        }
        return false;
    }

    public void resize(int x, int y){
        this.viewport.update(x, y, false);
        this.viewport.apply();
    }

    public void render(SpriteBatch batch){
        this.viewport.apply();
        setView(camera);
        int[] backgroundLayers = { 0, 1, 2, 3}; // don't allocate every frame!
        int[] foregroundLayers = { 4 };    // don't allocate every frame!
        this.renderer.render(backgroundLayers);
        batch.begin();
        player.draw(batch);
        batch.end();
        this.renderer.render(foregroundLayers);
    }
}
