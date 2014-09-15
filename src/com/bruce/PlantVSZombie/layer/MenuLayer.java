package com.bruce.PlantVSZombie.layer;

import com.bruce.PlantVSZombie.util.CommonUtil;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by Bruce
 * Data 2014/8/25
 * Time 16:22.
 */
public class MenuLayer extends BaseLayer {

    public MenuLayer() {
        init();
    }

    private void init() {
        CCSprite bg = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        bg.setAnchorPoint(0,0);
        this.addChild(bg);

        //菜单
        CCMenu menu = CCMenu.menu();
        CCMenuItemSprite itemSprite = CCMenuItemSprite.item(CCSprite.sprite("image/menu/start_adventure_default.png")
                ,CCSprite.sprite("image/menu/start_adventure_press.png"),this,"onClick");

        menu.addChild(itemSprite);
        menu.setScale(0.5f);
        menu.setPosition(winSize.width / 2 - 25, winSize.height / 2 - 110);
        menu.setRotation(4.5f);

        this.addChild(menu);

    }

    /**
     * 菜单点击事件必须要对象参数
     * @param o
     */
    public void onClick(Object o) {
        CommonUtil.changeLayer(new FightLayer());
    }
}
