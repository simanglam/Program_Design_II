package com.simanglam.fighting.bosswar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BossWarActor{
    TextureRegion textures[][];
    int healtPoint, current, forward, movingSpeed, direction;
    float accu;
    Rectangle position;

    public BossWarActor(String path, boolean enemy){
        init();
        textures = new TextureRegion[3][2];
        int xoffset = 5, yoffset = 12;
        Texture t = new Texture(Gdx.files.internal(path));
        for (int y = 0; y < 2; y++){
            for (int x = 0; x < 3; x++){
                textures[x][y] = new TextureRegion(t, 5 + ((14 + 10) * x), yoffset + ((14 + 12) * y), 14, 14);
            }
            yoffset *= (enemy) ? 2 : 1;
        }
    }

    public BossWarActor(TextureRegion[][] textureRegions, boolean enemy){
        init();
        this.textures = textureRegions;

    }

    private void init(){
        healtPoint = current = forward = 0;
        direction = -1;
        movingSpeed = 2;
        position = new Rectangle(0, 60, 14, 14);
    }

    public void update(float delta, boolean enemyInSight){
        forward = 0;
        accu += delta;
        if (accu >= 0.25){
            accu -= 0.25;
            current = (current + 1) % 3;
        }
        if (!enemyInSight){
            position.x += movingSpeed * 20 * delta * direction;
            forward = 1;
        }
        //System.out.println(String.format("%f %f %d %d", position.x));
    }

    public void draw(SpriteBatch batch){
        batch.draw(textures[current][forward], position.x, position.y);
    }

    public AttackInfo generateAttackInfo(){
        return new AttackInfo();
    }
}
