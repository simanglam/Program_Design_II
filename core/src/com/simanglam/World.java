package com.simanglam;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class World {
    public TiledMap tiledMap;
    TiledMapRenderer renderer;
    public OrthographicCamera camera;
    public Player player;
    public Viewport viewport;

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

    public void update(){
        player.updateX();
        playerCollideUpdate(true);
        player.updateY();
        playerCollideUpdate(false);
        float[] tempView = {0, 0, 0};
        tempView[0] = Math.max(Math.min(player.getPosition().x, ((int)tiledMap.getProperties().get("width") * (int)tiledMap.getProperties().get("tilewidth")) - (this.viewport.getWorldWidth() / 2)), 0 + (this.viewport.getWorldWidth() / 2));
        tempView[1] = Math.max(Math.min(player.getPosition().y, ((int)tiledMap.getProperties().get("height") * (int)tiledMap.getProperties().get("tileheight")) - (this.viewport.getWorldHeight() / 2)), 0 + (this.viewport.getWorldHeight() / 2));
        this.camera.position.set(tempView);
        this.camera.update();
    }

    public RectangleMapObject getCollideObject(Rectangle rectangle){
        MapLayer collisionObjectLayer = this.tiledMap.getLayers().get("物件層 1");
        MapObjects objects = collisionObjectLayer.getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle mapRectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(mapRectangle, rectangle)) {
                return rectangleObject;
            }
        }
        return null;
    }

    public MapObjects getCollideObjects(Rectangle rectangle){
        MapLayer collisionObjectLayer = this.tiledMap.getLayers().get("物件層 1");
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
        MapObjects rectangleMapObjects = getCollideObjects(playerRectangle);
        for (RectangleMapObject rObject : rectangleMapObjects.getByType(RectangleMapObject.class)){
            System.out.println(rObject.getProperties().get("portal"));
            if (rObject.getProperties().get("portal") != null){
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
                    player.setPosition(rectangle.x + rectangle.getWidth(), playerRectangle.y);
                }
                else if (player.isHeadRight()){
                    player.setPosition(rectangle.x - playerRectangle.getWidth(), playerRectangle.y);
                }
            }
            else{
                if (player.isHeadDown()){
                    player.setPosition(playerRectangle.x, rectangle.y + rectangle.getHeight());
                }
                else if (player.isHeadUP()){
                    player.setPosition(playerRectangle.x, rectangle.y - playerRectangle.getHeight());
                }
            }
        }
    }

    public void resize(int x, int y){
        this.viewport.update(x, y, false);
        this.viewport.apply();
    }

    public void render(SpriteBatch batch){
        this.viewport.apply();
        setView(camera);
        int[] backgroundLayers = { 0, 1 }; // don't allocate every frame!
        int[] foregroundLayers = { 2 };    // don't allocate every frame!
        this.renderer.render(backgroundLayers);
        batch.begin();
        player.draw(batch);
        batch.end();
        this.renderer.render(foregroundLayers);
    }
}
