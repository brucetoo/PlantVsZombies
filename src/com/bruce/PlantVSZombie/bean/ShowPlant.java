package com.bruce.PlantVSZombie.bean;

import org.cocos2d.nodes.CCSprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 9:54.
 */
public class ShowPlant {

    //自增长ID，图片信息，阳光 数
    private int id;
    private CCSprite defaultImg;
    private CCSprite bgImg;
    private int sunNum;

    //通过ID 找对应植物信息
    private static Map<Integer, HashMap<String, String>> data;

    static {
        data = new HashMap<Integer, HashMap<String, String>>();
        //植物信息的图片和阳光数
        for (int i = 1; i <= 9; i++) {
            HashMap<String, String> items = new HashMap<String, String>();
            items.put("fileName", String.format("image/fight/chose/choose_default%02d.png", i));
            items.put("sunNum", i * 25 + "");
            data.put(i, items);
        }
    }


    public ShowPlant(int id) {
        this.id = id;
        HashMap<String, String> item = data.get(id);
        defaultImg = CCSprite.sprite(item.get("fileName"));
        bgImg = CCSprite.sprite(item.get("fileName"));

        defaultImg.setAnchorPoint(0,0);
        bgImg.setAnchorPoint(0,0);

        //设置透明色
        bgImg.setOpacity(150);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CCSprite getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(CCSprite defaultImg) {
        this.defaultImg = defaultImg;
    }

    public CCSprite getBgImg() {
        return bgImg;
    }

    public void setBgImg(CCSprite bgImg) {
        this.bgImg = bgImg;
    }

    public int getSunNum() {
        return sunNum;
    }

    public void setSunNum(int sunNum) {
        this.sunNum = sunNum;
    }

    public static Map<Integer, HashMap<String, String>> getData() {
        return data;
    }

    public static void setData(Map<Integer, HashMap<String, String>> data) {
        ShowPlant.data = data;
    }
}
