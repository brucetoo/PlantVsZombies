package com.bruce.PlantVSZombie.bean.base;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 15:11.
 */
public abstract class Bullet extends BaseSprite {

    protected int attack = 10;// 攻击力
    protected int speed = 60;// 移动速度

    protected Bullet(String filepath) {
        super(filepath);
    }

    /**
     * 移动
     */
    public abstract void move();

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
