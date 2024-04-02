package com.simanglam;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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
        this.player.setPosition(this.camera.position.x, this.camera.position.y);
        this.viewport = new ExtendViewport(640, 480, camera);
    }

    public void setView(OrthographicCamera camera){
        this.renderer.setView(camera);
    }

    public void update(){
        player.updateX();
        playerCollideUpdate(true);
        player.updateY();
        playerCollideUpdate(false);
        this.camera.position.set(player.getPosition(), 10);
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

    private void playerCollideUpdate(boolean x){
        MapLayer collisionObjectLayer = this.tiledMap.getLayers().get("物件層 1");
        MapObjects objects = collisionObjectLayer.getObjects();
        Player player = this.player;
        Rectangle playerRectangle = player.getRectangle();
        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(playerRectangle, rectangle)) {
                System.out.println(rectangle);
                if (x){
                    if (player.isHeadLeft()){
                        player.setPosition(rectangle.x + rectangle.getWidth(), playerRectangle.y);
                    }
                    else{
                        player.setPosition(rectangle.x - playerRectangle.getWidth(), playerRectangle.y);
                    }
                }
                else{
                    if (player.isHeadDown()){
                        player.setPosition(playerRectangle.x, rectangle.y + rectangle.getHeight());
                    }
                    else{
                        player.setPosition(playerRectangle.x, rectangle.y - playerRectangle.getHeight());
                    }
                }
            }
        }
    }

    public void resize(int x, int y){
        this.viewport.update(x, y, true);
        this.viewport.apply();
    }

    public void render(SpriteBatch batch){
        this.viewport.apply();
        setView(camera);
        int[] backgroundLayers = { 0, 1 }; // don't allocate every frame!
        int[] foregroundLayers = { 2 };    // don't allocate every frame!
        //System.out.println(String.format("%s %s %s", this.tiledMap.getLayers().get(0).getName(), this.tiledMap.getLayers().get(1).getName(), this.tiledMap.getLayers().get(2).getName()));
        this.renderer.render(backgroundLayers);
        batch.begin();
        player.draw(batch);
        batch.end();
        this.renderer.render(foregroundLayers);
    }
}
