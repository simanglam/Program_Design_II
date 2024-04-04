package com.simanglam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements InputProcessor{
    static int MOVING_SPEED = 4;
    Rectangle rectangle;
    public Vector2 heading;
    Vector2 lastHeading;
    TextureRegion texturesRegions[][];
    int count = 0;
    float accu;
    public Player(){
        this.rectangle = new Rectangle(0, 0, 16, 16);
        this.heading = new Vector2(0, 0);
        this.lastHeading = this.heading.cpy();
        accu = 0f;
        Texture texture = new Texture("Cute RPG World/Characters/Character_043.png");
        texturesRegions = new TextureRegion[4][4];
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++)
                texturesRegions[x][y] = new TextureRegion(texture, 4 + ((16 + 8) * x), 7 + ((16 + 8) * y), 16, 16);

    }

    public void draw(SpriteBatch batch){
        int direction = 0;
        if ((int)heading.y == -1){
            direction = 0;
        }
        else if ((int)heading.x == -1){
            direction = 1;
        }
        else if ((int)heading.x == 1){
            direction = 2;
        }
        else if ((int)heading.y == 1){
            direction = 3;
        }
        else{
            direction = 0;
        }
        batch.draw(texturesRegions[count][direction], rectangle.x, rectangle.y);
        accu += Gdx.graphics.getDeltaTime();
        if ((heading.x != 0 || heading.y != 0)){
            if (accu >= 0.25){
                ++count;
                count %= 4;
                accu -= 0.25;
            }
        }
        else{
            count = 1;
            accu = 0;
        }
    }
    public void translate(float x, float y){ rectangle.x += x; rectangle.y += y; }
    public void setPosition(float x, float y){ rectangle.setPosition(x, y); }

    public boolean isHeadLeft(){return heading.x == -1;}
    public boolean isHeadDown(){return heading.y == -1;}
    public boolean isHeadRight(){return heading.x == 1;}
    public boolean isHeadUP(){return heading.y == 1;}
    public Vector2 getLastHeading(){return lastHeading;}

    public Vector2 getPosition(){return new Vector2(rectangle.x, rectangle.y);}
    public Rectangle creatInvestgateRectangle(){
        return new Rectangle((float)Math.floor(this.rectangle.x / 16) * 16 + lastHeading.x * 16, (float)Math.floor(this.rectangle.y / 16) * 16 + lastHeading.y * 16, 16, 16);
    }

    public void updateX(){
        Vector2 mVector2 = heading.cpy().scl(MOVING_SPEED);
        this.translate(mVector2.x, 0);
    }

    public void updateY(){
        Vector2 mVector2 = heading.cpy().scl(MOVING_SPEED);
        this.translate(0, mVector2.y);
    }

    public Rectangle getRectangle(){ return rectangle;}

    public void collide(Rectangle rectangle){

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode != Keys.A && keycode != Keys.S && keycode != Keys.W && keycode != Keys.D){
            return false;
        }
        if (keycode == Keys.A){
            heading.x -= 1;
        }
        else if (keycode == Keys.S){
            heading.y -= 1;
        }
        else if (keycode == Keys.D){
            heading.x += 1;
        }
        else if (keycode == Keys.W){
            heading.y += 1;
        }
        lastHeading.set(this.heading);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode != Keys.A && keycode != Keys.S && keycode != Keys.W && keycode != Keys.D){
            return false;
        }
        if (keycode == Keys.A){
            heading.x += 1;
        }
        else if (keycode == Keys.S){
            heading.y += 1;
        }
        else if (keycode == Keys.D){
            heading.x -= 1;
        }
        else if (keycode == Keys.W){
            heading.y -= 1;
        }
        return true;
    }
    @Override
    public boolean keyTyped(char character) { return false; }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }
}
