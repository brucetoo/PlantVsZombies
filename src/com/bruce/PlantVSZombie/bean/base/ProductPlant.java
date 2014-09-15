package com.bruce.PlantVSZombie.bean.base;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 15:16.
 */
public abstract class ProductPlant extends Plant {
    protected ProductPlant(String filepath) {
        super(filepath);
    }

    /**
     * 生成阳光
     */
    public abstract void create();
}
