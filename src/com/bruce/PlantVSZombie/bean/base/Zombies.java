package com.bruce.PlantVSZombie.bean.base;

import org.cocos2d.types.CGPoint;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 15:07.
 */
public abstract class Zombies extends BaseSprite{

    protected int life = 50;// 生命
    protected int attack = 10;// 攻击力
    protected int speed = 10;// 移动速度

    protected CGPoint startPoint;// 起点

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public CGPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(CGPoint startPoint) {
        this.startPoint = startPoint;
    }

    public CGPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(CGPoint endPoint) {
        this.endPoint = endPoint;
    }

    protected CGPoint endPoint;// 终点

    protected Zombies(String filepath) {
        super(filepath);

        setScale(0.5);
        setAnchorPoint(0.5f, 0);
    }

    /**
     * 移动
     */
    public abstract void move();

    /**
     * 攻击
     *
     * @param sprite:攻击植物，攻击僵尸
     */
    public abstract void attack(BaseSprite sprite);

    /**
     * 被攻击
     */
    public abstract void attacked(int attack);
}
