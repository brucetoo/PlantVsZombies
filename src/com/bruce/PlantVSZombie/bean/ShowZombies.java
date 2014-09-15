package com.bruce.PlantVSZombie.bean;

import com.bruce.PlantVSZombie.util.CommonUtil;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

import java.util.ArrayList;

/**
 * Created by Bruce
 * Data 2014/8/25
 * Time 17:54.
 */
public class ShowZombies extends CCSprite {
    private ArrayList<CCSpriteFrame> frames;//摇晃的序列帧

    public ShowZombies() {
        //必须实现父类的构造方法
        super("image/zombies/zombies_1/shake/z_1_01.png");
        shake();
    }

    private void shake() {
        CCAction repeatForeverAnimate = CommonUtil.getRepeatForeverAnimate(frames,
                                                                           2, "image/zombies/zombies_1/shake/z_1_%02d.png");
        this.runAction(repeatForeverAnimate);
    }
}
