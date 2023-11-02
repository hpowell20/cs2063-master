package mobiledev.unb.ca.sensorlistdemo

import android.app.Activity
import android.hardware.Sensor
import android.os.Bundle
import android.widget.TextView
import android.hardware.SensorManager
import android.view.View

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sensorListText = findViewById<TextView>(R.id.sensors_list)

        // Retrieve a list of the supported sensors on the device
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (currSensor in sensorList) {
            sensorListText.append(currSensor.name.trimIndent() + "\n")
        }
        sensorListText.visibility = View.VISIBLE
    }
}