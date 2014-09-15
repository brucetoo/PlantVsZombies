package com.bruce.PlantVSZombie.bean.zombie;

import com.bruce.PlantVSZombie.bean.base.BaseSprite;
import com.bruce.PlantVSZombie.bean.base.Plant;
import com.bruce.PlantVSZombie.bean.base.Zombies;
import com.bruce.PlantVSZombie.controller.GameController;
import com.bruce.PlantVSZombie.util.CommonUtil;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 16:20.
 */
public class NormolZombie extends Zombies {
    /**
     * 主要处理展示用的僵尸
     */
    public NormolZombie() {
        super("image/zombies/zombies_1/walk/z_1_01.png");
        baseAction();
    }

    /**
     * 主要用于运动的僵尸
     *
     * @param start
     * @param end
     */
    public NormolZombie(CGPoint start, CGPoint end) {
        super("image/zombies/zombies_1/walk/z_1_01.png");
        this.startPoint = start;
        this.endPoint = end;

        setPosition(start);
        move();
    }

    @Override
    public void move() {
        CCMoveTo moveTo = CCMoveTo.action(CGPointUtil.distance(startPoint, endPoint) / this.speed, endPoint);
        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "gameOver"));
        this.runAction(sequence);
        this.runAction(CommonUtil.getRepeatForeverAnimate(null, 7,
                                                          "image/zombies/zombies_1/walk/z_1_%02d.png"));
    }

    public void gameOver() {
        destroy();
        GameController.getInstance().gameOver();
    }

    private BaseSprite target;//记录正在攻击的植物
    private boolean isAttack = false; //记录僵尸的行为(是否在攻击)

    @Override
    public void attack(BaseSprite sprite) {

        if (!isAttack) {
            isAttack = true;
            target = sprite;//正在攻击的植物

            //停止所有的动作
            this.stopAllActions();
            //播放序列帧
            this.runAction(CommonUtil.getRepeatForeverAnimate(null, 10,
                                                              "image/zombies/zombies_1/attack/z_1_attack_%02d.png"));
            //植物生命值持续降低
            CCScheduler.sharedScheduler().schedule("attackPlant", this, 0.5f, false);
        }

    }

    /**
     * 植物被攻击，处理植物和僵尸动作
     *
     * @param t
     */
    public void attackPlant(float t) {

        if (target instanceof Plant) {
            Plant plant = (Plant) target;
            plant.attacked(attack);
            if(plant.getLife() <= 0){
                //停止吃植物
                CCScheduler.sharedScheduler().unschedule("attackPlant", this);
                //僵尸 空闲
                isAttack = false;
                //停止动作
                this.stopAllActions();
                //继续行走
                move();
            }
        }

    }

    @Override
    public void attacked(int attack) {

    }

    @Override
    public void baseAction() {
        CCAction repeatForeverAnimate = CommonUtil.getRepeatForeverAnimate(null,
                                                                           2, "image/zombies/zombies_1/shake/z_1_%02d.png");
        this.runAction(repeatForeverAnimate);
    }
}
