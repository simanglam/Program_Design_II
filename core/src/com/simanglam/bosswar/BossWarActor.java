package com.simanglam.bosswar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.simanglam.util.Const;

public class BossWarActor {
    Texture textures[][];
    int healtPoint, current, forward, movingSpeed, direction, attackCoolDown, ATK, animateLenth, money, spawnCooldown;
    float animateAccu, attackAccu, animateCooldown;
    boolean attackable;
    Rectangle position;
    Rectangle attackableRange;
    Rectangle visionRange;
    AttackInfo attackInfo;

    public BossWarActor(String path, boolean enemy) {
        Json json = new Json();
        CharacterInfo info = json.fromJson(CharacterInfo.class, Gdx.files.internal(path + "/bosswar-info.json"));

        textures = new Texture[3][info.image];
        String imagePrefix[] = {"idle", "team", "enemy"};
        for (int x = 0; x < imagePrefix.length; x++) {
            for (int y = 0; y < info.image; y++) {
                textures[x][y] = new Texture(path + "/image/" + imagePrefix[x] + "-" + y + ".png");
            }
        }

        attackable = false;
        healtPoint = info.healthPoint;
        current = forward = 0;
        animateAccu = 0;
        animateCooldown = info.animateCooldown;
        animateLenth = info.image;
        attackAccu = 0;
        attackCoolDown = info.attackCooldown;
        direction = (enemy) ? 1 : -1;
        movingSpeed = info.speed;
        ATK = info.ATK;
        money = info.money;
        spawnCooldown = info.spawnCooldown;

        position = new Rectangle(Const.maxViewportWidth, 120, info.size, info.size);
        if(enemy) position.x = 0;
        attackableRange = new Rectangle(position);
        attackableRange.width = info.range;
        visionRange = new Rectangle(position);
        visionRange.width = info.vision;
        this.attackInfo = new AttackInfo((direction < 0) ? "player" : "enemy", attackableRange, ATK);
    }

    public BossWarActor(BossWarActor bossWarActor) {
        textures = bossWarActor.textures;
        this.forward = bossWarActor.forward;
        this.current = bossWarActor.current;
        this.attackable = bossWarActor.attackable;
        healtPoint = bossWarActor.healtPoint;
        animateAccu = 0;
        animateCooldown = bossWarActor.animateCooldown;
        animateLenth = bossWarActor.animateLenth;
        attackAccu = 0;
        attackCoolDown = bossWarActor.attackCoolDown;
        direction = bossWarActor.direction;
        movingSpeed = bossWarActor.movingSpeed;
        ATK = bossWarActor.ATK;
        money = bossWarActor.money;
        spawnCooldown = bossWarActor.spawnCooldown;

        position = new Rectangle(bossWarActor.position);
        attackableRange = new Rectangle(bossWarActor.attackableRange);
        visionRange = new Rectangle(bossWarActor.visionRange);
        attackInfo = new AttackInfo((direction < 0) ? "player" : "enemy", attackableRange, ATK);
    }

    public void update(float delta, boolean enemyInSight) {
        forward = 0;
        animateAccu += delta;
        attackAccu += delta;
        if (animateAccu >= animateCooldown) {
            animateAccu -= animateCooldown;
            current = (current + 1) % animateLenth;
        }
        if (!enemyInSight) {
            position.x += movingSpeed * 20 * delta * direction;
            forward = (direction < 0) ? 1 : 2;
            attackable = false;
        } else {
            attackable = attackCoolDown <= attackAccu;
        }
        visionRange.x = (direction == 1) ? position.x + position.width : position.x - visionRange.width;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(textures[forward][current], position.x, position.y);
    }

    public boolean readyToAttack() {
        return attackable;
    }

    public Rectangle getVisionRange() {
        return visionRange;
    }

    public AttackInfo generateAttackInfo() {
        attackAccu = 0;
        attackableRange.x = (direction > 0) ? position.x + position.width : position.x - attackableRange.width;
        attackableRange.y = position.y;
        return attackInfo;
    }

    public boolean beingAttack(AttackInfo attackInfo) {
        if (Intersector.overlaps(position, attackInfo.demageRectangle)) {
            this.healtPoint -= attackInfo.damege;
            return true;
        }
        return false;
    }

    public void setPosition(float x, float y) {
        position.setPosition(x, y);
    }
}

class CharacterInfo{
    public int ATK, healthPoint, attackCooldown, speed, range, vision, image, size, animateCooldown, money, spawnCooldown;
}