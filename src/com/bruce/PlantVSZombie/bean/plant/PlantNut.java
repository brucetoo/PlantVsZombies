package com.bruce.PlantVSZombie.bean.plant;

import com.bruce.PlantVSZombie.bean.base.DefancePlant;
import com.bruce.PlantVSZombie.util.CommonUtil;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 19:56.
 */
public class PlantNut extends DefancePlant {

    public PlantNut() {
        super("image/plant/nut/p_3_01.png");
        baseAction();
    }

    @Override
    public void baseAction() {
        this.runAction(CommonUtil.getRepeatForeverAnimate(null, 11,
                                                          "image/plant/nut/p_3_%02d.png"));
    }
}
