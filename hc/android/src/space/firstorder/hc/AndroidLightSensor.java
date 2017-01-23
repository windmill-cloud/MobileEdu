package space.firstorder.hc;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by xuanwang on 1/22/17.
 */

public class AndroidLightSensor implements Main.LightSensorInterface, SensorEventListener {
    private float currentLux = 0;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public float getCurrentLux() {
        return currentLux;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.currentLux = event.values[0];
    }
}