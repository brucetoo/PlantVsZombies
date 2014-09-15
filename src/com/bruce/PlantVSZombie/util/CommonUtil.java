package com.bruce.PlantVSZombie.util;

import android.view.MotionEvent;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.*;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 通用工具
 * 
 * @author Administrator
 * 
 */
public class CommonUtil {

	/**
	 * 加载游戏地图
	 * @param tmxFile
	 * @return
	 */
	public static CCTMXTiledMap loadMap(String tmxFile)
	{
		CCTMXTiledMap gameMap = CCTMXTiledMap.tiledMap(tmxFile);

        //便于手动平移地图
		gameMap.setAnchorPoint(0.5f, 0.5f);
		CGSize contentSize = gameMap.getContentSize();
		gameMap.setPosition(contentSize.width / 2, contentSize.height / 2);
		
		return gameMap;
	}
	/**
	 * 从地图中加载指定名称的点集合
	 * @param map
	 * @param name
	 * @return
	 */
	public static List<CGPoint> loadPoint(CCTMXTiledMap map,String name)
	{
		CCTMXObjectGroup objectGroup = map.objectGroupNamed(name);
        // 获取僵尸位置信息
		ArrayList<HashMap<String, String>> objects = objectGroup.objects;
        // 分别以x和y为键，获取坐标值信息---->封装到点集合中
		List<CGPoint> points = new ArrayList<CGPoint>();
		for (HashMap<String, String> item : objects) {
			float x = Float.parseFloat(item.get("x"));
			float y = Float.parseFloat(item.get("y"));
			points.add(CGPoint.ccp(x, y));
		}
		return points;
	}
	
	
	
	/**
	 *序列帧播放(永不停止)
	 * 
	 * @param frames
	 * @param num
	 *          当前加载的图片数量
	 * @param filepath
	 *          路径（通用）
	 * @return
	 */
	public static CCAction getRepeatForeverAnimate(ArrayList<CCSpriteFrame> frames, int num, String filepath) {
		if (frames == null) {
			frames = new ArrayList<CCSpriteFrame>();
			for (int i = 1; i <= num; i++) {
				frames.add(CCSprite.sprite(String.format(filepath, i)).displayedFrame());
			}
		}
		CCAnimation anim = CCAnimation.animation("", 0.2f, frames);

		CCAnimate animate = CCAnimate.action(anim);
		return CCRepeatForever.action(animate);
	}

	/**
	 * 播放一次的序列帧
	 */
	public static CCAnimate getAnimate(ArrayList<CCSpriteFrame> frames, int num, String filepath) {
		if (frames == null) {
			frames = new ArrayList<CCSpriteFrame>();
			for (int i = 1; i <= num; i++) {
				frames.add(CCSprite.sprite(String.format(filepath, i)).displayedFrame());
			}
		}
		CCAnimation animation = CCAnimation.animation("",1, frames);
		return CCAnimate.action(animation, false);//只播放一次
	}

	/**
	 * 判断是否被点击
	 * 
	 * @param event
	 * @param node
	 * @return
	 */
	public static boolean isClicke(MotionEvent event, CCLayer layer, CCNode node) {
		CGPoint point = layer.convertTouchToNodeSpace(event);
		return CGRect.containsPoint(node.getBoundingBox(), point);
	}

	/**
	 *  切换场景,淡入淡出效果
	 * @param targetLayer
	 */
	public static void changeLayer(CCLayer targetLayer)
	{
		CCScene scene = CCScene.node();
		scene.addChild(targetLayer);
		CCFadeTransition transition = CCFadeTransition.transition(1, scene);
		CCDirector.sharedDirector().replaceScene(transition);
	}

}
