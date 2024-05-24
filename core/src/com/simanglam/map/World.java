package com.simanglam.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
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
import com.simanglam.util.Const;

public class World {
    public TiledMap tiledMap;
    TiledMapRenderer renderer;
    public OrthographicCamera camera;
    public Player player;
    public Viewport viewport;
    float accu;
    double ecounterPossibility;

    public World(){
        this.tiledMap = new TmxMapLoader().load("test.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.camera = new OrthographicCamera();
        this.player = new Player();
        this.viewport = new FitViewport(Const.maxViewportWidth, Const.maxViewportHeight, camera);
        camera.position.set(player.getPosition(), 0);
    }

    public void setView(OrthographicCamera camera){
        this.renderer.setView(camera);
    }

    public void update(float deltaT){
        player.updateX(deltaT);
        playerCollideUpdate(true);
        player.updateY(deltaT);
        playerCollideUpdate(false);
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

    private void playerCollideUpdate(boolean x){
        Rectangle playerRectangle = player.getRectangle();
        MapObjects rectangleMapObjects = getCollideObjects(playerRectangle, "物件層 1");
        for (RectangleMapObject rObject : rectangleMapObjects.getByType(RectangleMapObject.class)){
            if (rObject.getProperties().get("portal") != null){
                this.tiledMap.dispose();
                this.tiledMap = new TmxMapLoader().load((String)rObject.getProperties().get("next"));
                this.renderer = new OrthogonalTiledMapRenderer(tiledMap);
                for (RectangleMapObject rectangleObject : tiledMap.getLayers().get("物件層 1").getObjects().getByType(RectangleMapObject.class)) {
                    if (rectangleObject.getProperties().get("portal") != null && ((String)rectangleObject.getProperties().get("entry")).equals((String)rObject.getProperties().get("exit"))){
                        player.setPosition(rectangleObject.getRectangle().x - (float)rectangleObject.getProperties().get("entryX"), rectangleObject.getRectangle().y - (float)rectangleObject.getProperties().get("entryY"));
                        System.out.println(rectangleObject.getRectangle());
                        System.out.println(player.getPosition());
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
            ecounterPossibility += 1.0 / (int)((Math.random() * 50) + 1);
            System.out.println(ecounterPossibility);
            if (ecounterPossibility >= (int)(Math.random() * 50 + 1)){
                ecounterPossibility = 0;
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
