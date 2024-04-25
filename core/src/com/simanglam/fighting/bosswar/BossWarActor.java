package com.simanglam.fighting.bosswar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.simanglam.util.Const;

public class BossWarActor{
    TextureRegion textures[][];
    int healtPoint, current, forward, movingSpeed, direction, attackCoolDown, ATK;
    float accu, attackAccu;
    boolean attackable;
    Rectangle position;
    Rectangle attackableRange;
    Rectangle VisionRange;

    public BossWarActor(String path, boolean enemy){
        init(enemy);
        textures = new TextureRegion[3][2];
        int xoffset = 5, yoffset = 12;
        Texture t = new Texture(Gdx.files.internal(path));
        for (int y = 0; y < 2; y++){
            for (int x = 0; x < 3; x++){
                textures[x][y] = new TextureRegion(t, 5 + ((14 + 10) * x), yoffset + ((14 + 10) * y), 14, 14);
            }
            yoffset += (enemy) ? 24 : 0;
        }
        attackable = false;
    }

    public BossWarActor(TextureRegion[][] textureRegions, boolean enemy){
        init(enemy);
        this.textures = textureRegions;

    }

    private void init(boolean enemy){
        healtPoint = 10;
        current = forward = 0;
        attackAccu = 0;
        attackCoolDown = 2;
        direction = (enemy) ? 1 : -1;
        movingSpeed = 2;
        ATK = 10;
        position = new Rectangle(Const.maxViewportWidth, 60, 14, 14);
        attackableRange = new Rectangle(position);
        attackableRange.width = 100;
    }

    public void update(float delta, boolean enemyInSight){
        forward = 0;
        accu += delta;
        attackAccu += delta;
        if (accu >= 0.25){
            accu -= 0.25;
            current = (current + 1) % 3;
        }
        if (!enemyInSight){
            position.x += movingSpeed * 20 * delta * direction;
            forward = 1;
            attackable = false;
        }
        else{
            attackable = attackCoolDown <= attackAccu;
        }
    }

    public void draw(SpriteBatch batch){
        batch.draw(textures[current][forward], position.x, position.y);
    }

    public boolean readyToAttack(){
        return attackable;
    }

    public AttackInfo generateAttackInfo(){
        attackAccu = 0;
        attackableRange.x = (direction > 0) ? position.x : position.x - attackableRange.width;
        attackableRange.y = position.y;
        return new AttackInfo((direction < 0) ? "player" : "enemy", attackableRange, ATK);
    }

    public void beingAttack(AttackInfo attackInfo){
        if (Intersector.overlaps(position, attackInfo.demageRectangle)){
            this.healtPoint -= attackInfo.damege;
            System.out.println("Hello");
        }
    }

    public void setPosition(float x, float y){
        position.setPosition(x, y);
    }
}
