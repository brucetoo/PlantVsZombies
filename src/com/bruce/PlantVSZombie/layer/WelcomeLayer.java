package com.bruce.PlantVSZombie.layer;

import android.view.MotionEvent;
import com.bruce.PlantVSZombie.util.CommonUtil;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;

import java.util.ArrayList;

/**
 * Created by Bruce
 * Data 2014/8/25
 * Time 11:10.
 */
public class WelcomeLayer extends BaseLayer {


    public WelcomeLayer() {
      // consumeTime();
        init();
    }



    private void init() {
        logo();
    }

    private void logo() {
        CCSprite logo = CCSprite.sprite("image/popcap_logo.png");
        logo.setPosition(winSize.getWidth() / 2, winSize.getHeight() / 2);
        this.addChild(logo);

        CCSequence sequence = CCSequence.actions(CCDelayTime.action(1),
                                                 CCHide.action(), CCDelayTime.action(0.5f),
                                                 CCCallFunc.action(this, "loadInfo"));

        logo.runAction(sequence);

    }

    private CCSprite startGame;

    /**
     * 加载进度条和背景图片
     */
    public void loadInfo() {
        //欢迎界面
        CCSprite bg = CCSprite.sprite("image/welcome.jpg");
        bg.setAnchorPoint(0, 0);
        this.addChild(bg);

        //滚动条
        CCSprite loading = CCSprite.sprite("image/loading/loading_01.png");
        loading.setPosition(winSize.getWidth() / 2, 40);
        this.addChild(loading);

        //序列帧播放
        ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
        String fileName = "image/loading/loading_%02d.png";
        for (int i = 1; i <= 9; i++) {
            frames.add(CCSprite.sprite(String.format(fileName, i)).displayedFrame());
        }

        CCAnimation anim = CCAnimation.animation("", 0.2f, frames);
        //第二个参数：控制循环播放序列帧，默认为true
        CCAnimate animate = CCAnimate.action(anim, false);
       // CCSequence sequence = CCSequence.actions(animate,CCDelayTime.action(2),CCHide.action());
        loading.runAction(animate);


        startGame = CCSprite.sprite("image/loading/loading_start.png");
        startGame.setPosition(winSize.getWidth() / 2, 40);
        this.addChild(startGame);
        CCSequence sequence = CCSequence.actions(CCHide.action(), CCDelayTime.action(2), CCShow.action());
        startGame.runAction(sequence);

        this.setIsTouchEnabled(true);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        if(CommonUtil.isClicke(event,this,startGame)){

            //淡入淡出的效果,替换场景
            CCScene scene = CCScene.node();
            scene.addChild(new MenuLayer());

            CCFadeTransition transition = CCFadeTransition.transition(1,scene);
            CCDirector.sharedDirector().replaceScene(transition);


        }
        return super.ccTouchesBegan(event);
    }
}
