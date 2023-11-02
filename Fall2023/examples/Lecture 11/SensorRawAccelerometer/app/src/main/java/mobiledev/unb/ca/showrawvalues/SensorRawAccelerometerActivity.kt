package mobiledev.unb.ca.showrawvalues

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView
import android.os.Bundle
import android.hardware.SensorEvent
class SensorRawAccelerometerActivity : Activity(), SensorEventListener {
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mXValueView: TextView? = null
    private var mYValueView: TextView? = null
    private var mZValueView: TextView? = null
    private var mLastUpdate: Long = 0
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        mXValueView = findViewById(R.id.x_value_view)
        mYValueView = findViewById(R.id.y_value_view)
        mZValueView = findViewById(R.id.z_value_view)

        // Get reference to SensorManager
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Get reference to Accelerometer
        if (null != mSensorManager) {
            mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        if (null == mAccelerometer) {
            finish()
        }
    }

    // Register listener
    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
        mLastUpdate = System.currentTimeMillis()
    }

    // Unregister listener
    override fun onPause() {
        mSensorManager!!.unregisterListener(this)
        super.onPause()
    }

    // Process new reading
    override fun onSensorChanged(event: SensorEvent) {
        // Do something with this sensor value
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val actualTime = System.currentTimeMillis()
            if (actualTime - mLastUpdate > UPDATE_THRESHOLD) {
                mLastUpdate = actualTime
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                mXValueView!!.text = x.toString()
                mYValueView!!.text = y.toString()
                mZValueView!!.text = z.toString()
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes
    }
    companion object {
        private const val UPDATE_THRESHOLD = 500
    }
}