package com.bruce.PlantVSZombie.bean.base;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 15:17.
 */
public abstract class DefancePlant extends Plant {
    protected DefancePlant(String filepath) {
        super(filepath);
        life = 200;
    }
}
