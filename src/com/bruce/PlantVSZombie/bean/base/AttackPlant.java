package com.bruce.PlantVSZombie.bean.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 15:10.
 * 攻击性植物
 */
public abstract class AttackPlant extends Plant{

    // 弹夹
    protected List<Bullet> bullets = new ArrayList<Bullet>();

    protected AttackPlant(String filepath) {
        super(filepath);
    }

    /**
     * 生产用于攻击的子弹
     * @return
     */
    public abstract Bullet createBullet();

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }
}
