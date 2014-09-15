package com.bruce.PlantVSZombie;

import android.app.Activity;
import android.os.Bundle;
import com.bruce.PlantVSZombie.layer.FightLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {

    private CCDirector director;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        director = CCDirector.sharedDirector();
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);

        director.attachInView(surfaceView);

        //屏幕方向--横屏
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        //屏幕大小
        director.setScreenSize(480, 320);

        director.setDisplayFPS(true);

        //WelcomeLayer layer = new WelcomeLayer();

        CCScene scene = CCScene.node();
        scene.addChild(new FightLayer());

        director.runWithScene(scene);
    }


    @Override
    protected void onResume() {
        director.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        director.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        director.end();
        super.onDestroy();
    }
}
