package edu.ucsb.xuanwang.sensorsdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mAmbientTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        mAmbientTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorManager.registerListener(this, mAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        StringBuilder stringBuilder = new StringBuilder();
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            stringBuilder.append("X: ").append(event.values[0]).append(", ");
            stringBuilder.append("Y: ").append(event.values[1]).append(", ");
            stringBuilder.append("Z: ").append(event.values[2]);

            TextView accelerometerText = (TextView) findViewById(R.id.accelerometer_text);
            accelerometerText.setText(stringBuilder.toString());
        }
        else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            stringBuilder.append(event.values[0]);
            TextView tempText = (TextView) findViewById(R.id.temperature_text);
            tempText.setText(stringBuilder.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
