package com.bruce.PlantVSZombie.controller;

import android.view.MotionEvent;
import com.bruce.PlantVSZombie.bean.ShowPlant;
import com.bruce.PlantVSZombie.bean.base.Plant;
import com.bruce.PlantVSZombie.bean.plant.PlantNut;
import com.bruce.PlantVSZombie.bean.zombie.NormolZombie;
import com.bruce.PlantVSZombie.layer.FightLayer;
import com.bruce.PlantVSZombie.util.CommonUtil;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 13:56.
 */
public class GameController {
    private static GameController instance = new GameController();

    private GameController() {
    }

    public static GameController getInstance() {
        return instance;
    }

    private static List<FightLine> fightLines;

    //初始化五行对战
    static {
        fightLines = new ArrayList<FightLine>();
        for (int line = 0; line <= 4; line++) {
            FightLine fightLine = new FightLine(line);
            fightLines.add(fightLine);
        }
    }


    private boolean isStart = false;
    private CCTMXTiledMap gameMap;  //地图
    private List<ShowPlant> chosePlantList; //选择植物集合
    private FightLayer layer; //通过gameMap来获取

    private CGPoint[][] towers = new CGPoint[5][9];//植物安放点的二维数组

    /**
     * 开始游戏
     *
     * @param gameMap
     * @param chosePlantList
     */
    public void start(CCTMXTiledMap gameMap, List<ShowPlant> chosePlantList) {
        isStart = true;
        this.gameMap = gameMap;
        this.chosePlantList = chosePlantList;
        this.layer = (FightLayer) gameMap.getParent();

        loadPlantPos();

        //添加一个僵尸
        //addZombies();
        //定时添加一个僵尸
        CCScheduler.sharedScheduler().schedule("addZombies", this, 3, false);
    }

    /**
     * 解析植物的安放位置
     */
    private void loadPlantPos() {

        for (int i = 1; i <= 5; i++) {
            CCTMXObjectGroup objectGroupNamed = gameMap.objectGroupNamed(String
                                                                                 .format("tower0%d", i));
            for (int j = 0; j < objectGroupNamed.objects.size(); j++) {
                HashMap<String, String> item = objectGroupNamed.objects.get(j);
                towers[i - 1][j] = CGPoint.ccp(Integer.parseInt(item.get("x")),
                                               Integer.parseInt(item.get("y")));
            }
        }
    }

    /**
     * 随机在某行添加一个僵尸
     *
     * @param f
     */
    public void addZombies(float f) {
        Random random = new Random();
        //第几行
        int lineNum = random.nextInt(5);
        //起点，终点

        int startIndex = lineNum * 2;
        int endIndex = lineNum * 2 + 1;

        //获取地图上每个僵尸的移动点
        List<CGPoint> road = CommonUtil.loadPoint(gameMap, "road");
        CGPoint startPoint = road.get(startIndex);
        CGPoint endPoint = road.get(endIndex);

        NormolZombie zombie = new NormolZombie(startPoint, endPoint);
        layer.addChild(zombie,1);
        fightLines.get(lineNum).addZombies(zombie);
    }

    /**
     * 游戏结束
     */
    public void gameOver() {

    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }


    private ShowPlant currentPlant;//展示用的植物
    private Plant plants;//放到地图的植物

    /**
     * 处理点击事件
     *
     * @param event
     */
    public void handleTouch(MotionEvent event) {

        if (CommonUtil.isClicke(event, layer, layer.getChildByTag(FightLayer.TAG_CHOSE))) {
            //选择植物
            for (ShowPlant plant : chosePlantList) {
                if (CommonUtil.isClicke(event, layer, plant.getDefaultImg())) {
                    currentPlant = plant;
                    currentPlant.getDefaultImg().setOpacity(150);

                    //根据ID来识别是哪个植物被选择
                    switch (plant.getId()) {
                        case 4:
                            plants = new PlantNut();
                            break;
                    }
                }
            }
        } else {
            //安放植物
            if (currentPlant != null) {
                // plants.setPosition(layer.convertTouchToNodeSpace(event));
                //判断植物安放位置： 水平 1-9，竖直 1-5
                if (isBuild(layer.convertTouchToNodeSpace(event))) {
                    addPlant();
                }
            }
        }
    }

    /**
     * 添加植物
     */
    private void addPlant() {
        //通过当前的列获取到 行战场
        int line = plants.getLine();
        FightLine fightLine = fightLines.get(line);

        if (fightLine.isAdd(plants)) {

            fightLine.addPlant(plants);

            layer.addChild(plants);
            currentPlant.getDefaultImg().setOpacity(255);
            //安放后 null
            currentPlant = null;
            plants = null;
        }
    }

    /**
     * 判断点击的点  是否在植物安放的block中
     * @param point
     * @return
     */
    private boolean isBuild(CGPoint point) {

        int blockRow = (int) (point.x / 46);
        int blockLine = (int) ((CCDirector.sharedDirector().getWinSize().getHeight() - point.y) / 54);

        if (blockRow >= 1 && blockRow <= 9) {
            if (blockLine >= 1 && blockLine <= 5) {
                // 植物安放的行和列信息
                int row = blockRow - 1;
                int line = blockLine - 1;

                plants.setLine(line);
                plants.setRow(row);

                plants.setPosition(towers[line][row]);
                return true;
            }
        }
        return false;
    }
}
