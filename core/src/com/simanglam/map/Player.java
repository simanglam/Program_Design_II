package com.simanglam.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements InputProcessor{
    static int MOVING_SPEED = 4;
    Rectangle rectangle;
    Rectangle invesgateRectangle;
    public Vector2 heading;
    Vector2 lastHeading;
    TextureRegion texturesRegions[][];
    int count = 0;
    float accu;
    public Player(){
        this.rectangle = new Rectangle(16, 16, 16, 16);
        invesgateRectangle = new Rectangle(rectangle);
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
        Vector2 currentHeading;
        if (heading.y != 0 || heading.x != 0)
            currentHeading = heading;
        else
            currentHeading = lastHeading;
        if ((int)currentHeading.y == -1){
            direction = 0;
        }
        else if ((int)currentHeading.x == -1){
            direction = 1;
        }
        else if ((int)currentHeading.x == 1){
            direction = 2;
        }
        else if ((int)currentHeading.y == 1){
            direction = 3;
        }
        else{
            direction = 0;
        }
        batch.draw(texturesRegions[count][direction], rectangle.x, rectangle.y);
        accu += Gdx.graphics.getDeltaTime();
        if (currentHeading == heading){
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
        invesgateRectangle.setPosition(this.rectangle.x + lastHeading.x * 16, this.rectangle.y + lastHeading.y * 16);
        return invesgateRectangle;
    }

    public void updateX(float deltaT){
        Vector2 mVector2 = heading.cpy().scl(MOVING_SPEED * 45 * deltaT);
        this.translate(mVector2.x, 0);
    }

    public void updateY(float deltaT){
        Vector2 mVector2 = heading.cpy().scl(MOVING_SPEED * 45 * deltaT);
        this.translate(0, mVector2.y);
    }

    public Rectangle getRectangle(){return rectangle;}

    @Override
    public boolean keyDown(int keycode) {
        if (keycode != Keys.A && keycode != Keys.S && keycode != Keys.W && keycode != Keys.D){
            return false;
        }
        if (keycode == Keys.A){
            heading.x -= (heading.x >= 0) ? 1 : 0;
        }
        else if (keycode == Keys.S){
            heading.y -= (heading.y >= 0) ? 1 : 0;
        }
        else if (keycode == Keys.D){
            heading.x += (heading.x <= 0) ? 1 : 0;
        }
        else if (keycode == Keys.W){
            heading.y += (heading.y <= 0) ? 1 : 0;
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
            heading.x += (heading.x < 0) ? 1 : 0;
        }
        else if (keycode == Keys.S){
            heading.y += (heading.y < 0) ? 1 : 0;
        }
        else if (keycode == Keys.D){
            heading.x -= (heading.x > 0) ? 1 : 0;
        }
        else if (keycode == Keys.W){
            heading.y -= (heading.y > 0) ? 1 : 0;
        }
        return true;
    }

    public void freeze(){
        this.heading.x = 0;
        this.heading.y = 0;
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
