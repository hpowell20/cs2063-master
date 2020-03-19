package mobiledev.unb.ca.sensorlistdemo;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView sensorListText = findViewById(R.id.sensor_list_text_view);

        // Retrieve a list of the supported sensors on the device
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 1; i < sensorList.size(); i++) {
            Sensor currSensor = sensorList.get(i);
            sensorListText.append("Sensor Name: " + currSensor.getName() +
                    "\nVendor Name: " + currSensor.getVendor() +
                    "\nVersion: " + currSensor.getVersion() + "\n\n");
        }
    }

}
