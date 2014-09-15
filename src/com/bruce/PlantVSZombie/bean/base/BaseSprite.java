package com.bruce.PlantVSZombie.bean.base;

import com.bruce.PlantVSZombie.controller.DieListener;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 14:57.
 * 公共精灵
 */
public abstract  class BaseSprite  extends CCSprite{
    private DieListener listener; //生命值变化实现该接口

    public void setListener(DieListener listener) {
        this.listener = listener;
    }

    protected BaseSprite(String filepath) {
        super(filepath);
    }

    /**
     * 精灵的基本动作
     */
    public abstract void baseAction();

    /**
     * 销毁-死亡
     */
    public void destroy(){
        if(listener != null){
            listener.die(this);
        }
        this.removeSelf();
    }
}
