package edu.ucsb.ece150.demos.sensorsdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        Sensor accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null) {
            mSensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
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
        else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            stringBuilder.append(event.values[0]);
            TextView tempText = (TextView) findViewById(R.id.temperature_text);
            tempText.setText(stringBuilder.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
