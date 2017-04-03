package edu.ucsb.ece150.demos.gdxdemo;

/**
 * Created by xuanwang on 4/2/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class DemoInputProcessor extends CameraInputController {
    final Runnable customAction;

    public DemoInputProcessor(Camera camera, Runnable changeColors) {
        super(camera);
        customAction = changeColors;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("Example", "touch started at (" + screenX + ", " + screenY + ")");
        if (screenX <= 250f && screenY >= 800f) {
            if (customAction != null) {

                // call the changeColorsAction runnable interface, implemented in Main
                customAction.run();
                return true;
            }
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
