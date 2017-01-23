package space.firstorder.hc;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import space.firstorder.hc.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		AndroidLightSensor sensor = new AndroidLightSensor();
        /*
		config.useGyroscope = true;  //default is false

        //you may want to switch off sensors that are on by default if they are no longer needed.
		config.useAccelerometer = false;
		config.useCompass = false;
        */
		initialize(new Main(sensor), config);
	}
}
