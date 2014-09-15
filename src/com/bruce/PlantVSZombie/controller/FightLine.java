package com.bruce.PlantVSZombie.controller;

import com.bruce.PlantVSZombie.bean.base.AttackPlant;
import com.bruce.PlantVSZombie.bean.base.BaseSprite;
import com.bruce.PlantVSZombie.bean.base.Plant;
import com.bruce.PlantVSZombie.bean.base.Zombies;
import org.cocos2d.actions.CCScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 15:34.
 */
public class FightLine implements DieListener{

    private int lineNum;
    private List<Zombies> zombiesList;// 当前行添加的僵尸集合

    private Map<Integer, Plant> plants;// 当前行添加的植物集合
    private List<AttackPlant> attackPlantList;// 当前行添加的攻击型植物

    public FightLine(int lineNum) {
        super();
        this.lineNum = lineNum;
        zombiesList = new ArrayList<Zombies>();
        plants = new HashMap<Integer, Plant>();
        attackPlantList = new ArrayList<AttackPlant>();

        //执行僵尸攻击植物
        CCScheduler.sharedScheduler().schedule("attackPlant", this, 0.5f, false);
    }

    /**
     * 添加僵尸
     *
     * @param zombies
     */
    public void addZombies(Zombies zombies) {
        //为僵尸添加死亡监听
        zombies.setListener(this);
        zombiesList.add(zombies);
    }

    /**
     * 添加攻击性植物
     *
     * @param plant
     */
    public void addPlant(Plant plant) {
        //为植物添加死亡监听
        plant.setListener(this);
        //添加植物
        plants.put(plant.getRow(), plant);
        //添加攻击型植物
        if (plant instanceof AttackPlant) {
            attackPlantList.add((AttackPlant) plant);
        }
    }

    /**
     * 僵尸攻击植物
     */
    public void attackPlant(float t) {
        if (plants.size() > 0 && zombiesList.size() > 0) {
            for (Zombies zombies : zombiesList) {
                //判断僵尸现在的x位置（块为单位）
                int x = (int) (zombies.getPosition().x / 46 - 1);
                Plant plant = plants.get(x);
                //如果该位置有植物
                if (plant != null) {
                    //僵尸攻击植物
                   zombies.attack(plant);
                }
            }
        }
    }

    /**
     * 植物攻击僵尸
     */
    public void attackZombie() {
        if (attackPlantList.size() > 0 && zombiesList.size() > 0) {

        }
    }

    /**
     * 判断该列能否添加植物（）
     *
     * @param plant
     * @return
     */
    public boolean isAdd(Plant plant) {
        return !plants.containsKey(plant.getRow());
    }

    @Override
    public void die(BaseSprite sprite) {
        if (sprite instanceof Plant) {
            int key = ((Plant) sprite).getRow();
            plants.remove(key);

            if (sprite instanceof AttackPlant) {
                attackPlantList.remove(sprite);
            }
        } else {
            zombiesList.remove(sprite);
        }
    }
}
