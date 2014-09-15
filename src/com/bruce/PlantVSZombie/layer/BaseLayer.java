package com.bruce.PlantVSZombie.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * Created by Bruce
 * Data 2014/8/25
 * Time 11:47.
 */
public abstract class BaseLayer extends CCLayer {
    //获取屏幕的size
    protected CGSize winSize;
    public BaseLayer() {
        winSize = CCDirector.sharedDirector().getWinSize();
    }
}
