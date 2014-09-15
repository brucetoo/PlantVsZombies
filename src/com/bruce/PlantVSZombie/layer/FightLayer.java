package com.bruce.PlantVSZombie.layer;

import android.view.MotionEvent;
import com.bruce.PlantVSZombie.bean.ShowPlant;
import com.bruce.PlantVSZombie.bean.ShowZombies;
import com.bruce.PlantVSZombie.controller.GameController;
import com.bruce.PlantVSZombie.util.CommonUtil;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Bruce
 * Data 2014/8/25
 * Time 16:44.
 */
public class FightLayer extends BaseLayer {
    public static final int TAG_CHOSE = 10;
    // 1、地图展示（无法处理touch事件）
    // ①展示对战地图（展示时间）
    // ②加载展示用的僵尸(展示用点位)
    // ③移动地图（停留一段时间）
    // ④展示两个容器：玩家已有植物容器（多）；玩家已选植物容器（少）
    private CCTMXTiledMap gameMap;
    private List<ShowZombies> showZombiesList; //展示僵尸集合

    CCSprite chooseContainer; //大容器
    CCSprite choseContainer;  //小 已选容器

    private List<ShowPlant> showPlantList; //展示植物集合
    private List<ShowPlant> chosePlantList;//已有植物集合

    private CCSprite start;//开始图片

    public FightLayer() {
        // setIsTouchEnabled(true);
        init();
    }

    private void init() {
        loadMap();
        loadShowZombies();
        moveMap();
    }

    /**
     * 移动地图
     */
    private void moveMap() {

        CGPoint pos = CGPoint.ccp(gameMap.getPosition().x - (gameMap.getContentSize().getWidth() -
                winSize.getWidth()), gameMap.getPosition().y);
        CCMoveTo moveTo = CCMoveTo.action(1, pos);
        CCSequence sequence = CCSequence.actions(CCDelayTime.action(1), moveTo,
                                                 CCDelayTime.action(0.5f)
                , CCCallFunc.action(this, "loadContainer"));
        gameMap.runAction(sequence);
    }

    /**
     * 显示容器
     */
    public void loadContainer() {
        chooseContainer = CCSprite.sprite("image/fight/chose/fight_choose.png");
        choseContainer = CCSprite.sprite("image/fight/chose/fight_chose.png");

        choseContainer.setAnchorPoint(0, 1);
        choseContainer.setPosition(0, winSize.height);

        chooseContainer.setAnchorPoint(0, 0);
        this.addChild(choseContainer,0,TAG_CHOSE); //第二个参数，0,1表示优先级
        this.addChild(chooseContainer, 1);

        //加载植物信息
        loadPlant();

        //添加开始图片
        start = CCSprite.sprite("image/fight/chose/fight_start.png");
        start.setPosition(chooseContainer.getContentSize().getWidth() / 2, 40);
        chooseContainer.addChild(start);

    }

    /**
     * 加载植物信息
     */
    private int row = 4;

    private void loadPlant() {
        //展示用的植物信息
        showPlantList = new ArrayList<ShowPlant>();
        //。。。。
        chosePlantList = new CopyOnWriteArrayList<ShowPlant>();
        for (int i = 1; i <= 9; i++) {

            //传入ID时就初始化了所以的植物信息
            ShowPlant plant = new ShowPlant(i);
            CCSprite defaultImg = plant.getDefaultImg();
            CCSprite bgImg = plant.getBgImg();
            defaultImg.setPosition((i - 1) % row * 54 + 16,
                                   175 - (i - 1) / row * 59);
            bgImg.setPosition((i - 1) % row * 54 + 16,
                              175 - (i - 1) / row * 59);
            chooseContainer.addChild(defaultImg);
            chooseContainer.addChild(bgImg);
            showPlantList.add(plant);
        }

        setIsTouchEnabled(true);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {

        if(GameController.getInstance().isStart()){
           //如果游戏开始,防止游戏点击事件冲突
            GameController.getInstance().handleTouch(event);
        }else {
            //没有开始游戏
            if (CommonUtil.isClicke(event, this, choseContainer)) {
                boolean isDel = false; //记录是否有删除条目
                //点击的是已选容器
                for (ShowPlant plant : chosePlantList) {
                    if (CommonUtil.isClicke(event, this, plant.getDefaultImg())) {
                        CCMoveTo moveTo = CCMoveTo.action(0.3f, plant.getBgImg().getPosition());
                        plant.getDefaultImg().runAction(moveTo);
                        chosePlantList.remove(plant);
                        isDel = true;
                    }

                    if (isDel) {
                        //有条目删除，后面的item依次向前顶一格
                        plant.getDefaultImg().setPosition(plant.getDefaultImg().getPosition().x - 54,
                                                          plant.getDefaultImg().getPosition().y);
                    }
                }
            } else {
                //点击开始图片,开启游戏
                if (CommonUtil.isClicke(event, this, start)) {
                    setIsTouchEnabled(false); //不能点击屏幕
                    preGame();

                } else {
                    //点击选择容器
                    for (ShowPlant plant : showPlantList) {
                        if (CommonUtil.isClicke(event, this, plant.getDefaultImg()) && chosePlantList.size() < 5) {
                            CGPoint pos = CGPoint.ccp(75 + chosePlantList.size() * 54, winSize.height - 65);
                            CCMoveTo moveTo = CCMoveTo.action(0.3f, pos);
                            plant.getDefaultImg().runAction(moveTo);
                            chosePlantList.add(plant); //已选集合
                        }
                    }
                }
            }
        }


        return super.ccTouchesBegan(event);
    }

    /**
     * 开启游戏前的准备
     */
    private void preGame() {

        //1.回收容器
        chooseContainer.removeSelf();

        // 玩家已有植物容器进行缩放（包含所有已经选择的植物信息）
        choseContainer.setScale(0.65f);

        //用已选择的植物填充容器,对容器做缩放操作
        for(ShowPlant item : chosePlantList){
            CCSprite sprite = item.getDefaultImg();
            CGPoint pos = CGPoint.ccp(sprite.getPosition().x*0.65f,
                                      sprite.getPosition().y+
                                              (winSize.getHeight()-sprite.getPosition().y)*0.35f);
            sprite.setPosition(pos);
            sprite.setScale(0.65f);
            this.addChild(sprite);
        }

        //2.移动地图
        CGPoint pos = CGPoint.ccp(gameMap.getContentSize().getWidth() / 2, gameMap.getContentSize().getHeight() / 2);
        CCMoveTo moveTo = CCMoveTo.action(1, pos);
        gameMap.runAction(moveTo);

        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "clearZombies"));
        //3.回收僵尸
        gameMap.runAction(sequence);

    }

    /**
     * 回收僵尸
     */
    public void clearZombies() {

        for (ShowZombies item : showZombiesList) {
            item.removeSelf();
        }
        showZombiesList.clear();
        showPlantList = null;

        //播放序列帧，，开始游戏
        ready();
    }

    private CCSprite ready;
    /**
     * 播放序列帧，，开始游戏
     */
    private void ready() {
        ready = CCSprite.sprite("image/fight/startready_01.png");

        ArrayList<CCSpriteFrame> frames = null;
        ready.setPosition(winSize.getWidth() / 2, winSize.getHeight() / 2);
        this.addChild(ready);
        CCAnimate animate = CommonUtil.getAnimate(frames, 3, "image/fight/startready_%02d.png");
        CCSequence sequence = CCSequence.actions(animate, CCCallFunc.action(this, "go"));
        ready.runAction(sequence);
    }

    /**
     * 开始游戏
     */
    public void go() {

        //开始游戏前，移除序列帧
        ready.removeSelf();

        //开始游戏后，屏幕可点击
        setIsTouchEnabled(true);

        //游戏业务处理
        GameController.getInstance().start(gameMap,chosePlantList);
    }

    /**
     * 加载展示用的僵尸，存放在集合中
     */
    private void loadShowZombies() {
        showZombiesList = new ArrayList<ShowZombies>();
        List<CGPoint> zombies = CommonUtil.loadPoint(gameMap, "zombies");
        for (CGPoint item : zombies) {
            ShowZombies showZombies = new ShowZombies();
            showZombies.setPosition(item);
            showZombies.setScale(0.4f);
            //加入到地图，
            gameMap.addChild(showZombies);
            showZombiesList.add(showZombies);
        }
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        gameMap = CommonUtil.loadMap("image/fight/map_day.tmx");
        this.addChild(gameMap);
    }

//    @Override
//    public boolean ccTouchesMoved(MotionEvent event) {
    //测试屏幕移动
//        gameMap.touchMove(event, gameMap);
//        return super.ccTouchesMoved(event);
//    }
}
