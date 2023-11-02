package mobiledev.unb.ca.showfilteredvalues

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView
import android.os.Bundle
import android.hardware.SensorEvent
class SensorFilteredValuesActivity : Activity(), SensorEventListener {
    // References to SensorManager and accelerometer
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    // Arrays for storing filtered values
    private val mGravity = FloatArray(3)
    private val mAccel = FloatArray(3)

    // Text views for displaying the values
    private var mXValueView: TextView? = null
    private var mYValueView: TextView? = null
    private var mZValueView: TextView? = null
    private var mXGravityView: TextView? = null
    private var mYGravityView: TextView? = null
    private var mZGravityView: TextView? = null
    private var mXAccelView: TextView? = null
    private var mYAccelView: TextView? = null
    private var mZAccelView: TextView? = null

    private var mLastUpdate: Long = 0
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Get references to the TextView objects
        mXValueView = findViewById(R.id.x_value_view)
        mYValueView = findViewById(R.id.y_value_view)
        mZValueView = findViewById(R.id.z_value_view)
        mXGravityView = findViewById(R.id.x_lowpass_view)
        mYGravityView = findViewById(R.id.y_lowpass_view)
        mZGravityView = findViewById(R.id.z_lowpass_view)
        mXAccelView = findViewById(R.id.x_highpass_view)
        mYAccelView = findViewById(R.id.y_highpass_view)
        mZAccelView = findViewById(R.id.z_highpass_view)

        // Get reference to SensorManager
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (null != mSensorManager) {
            // Get reference to Accelerometer
            mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            if (null == mAccelerometer) {
                finish()
            }
            mLastUpdate = System.currentTimeMillis()
        }
    }

    // Register listener
    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mAccelerometer,
            SensorManager.SENSOR_DELAY_UI)
    }

    // Unregister listener
    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    // Process new reading
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val actualTime = System.currentTimeMillis()
            if (actualTime - mLastUpdate > 500) {
                mLastUpdate = actualTime
                val rawX = event.values[0]
                val rawY = event.values[1]
                val rawZ = event.values[2]

                // Apply low-pass filter
                mGravity[0] = lowPass(rawX, mGravity[0])
                mGravity[1] = lowPass(rawY, mGravity[1])
                mGravity[2] = lowPass(rawZ, mGravity[2])

                // Apply high-pass filter
                mAccel[0] = highPass(rawX, mGravity[0])
                mAccel[1] = highPass(rawY, mGravity[1])
                mAccel[2] = highPass(rawZ, mGravity[2])

                mXValueView!!.text = rawX.toString()
                mYValueView!!.text = rawY.toString()
                mZValueView!!.text = rawZ.toString()

                mXGravityView!!.text = mGravity[0].toString()
                mYGravityView!!.text = mGravity[1].toString()
                mZGravityView!!.text = mGravity[2].toString()

                mXAccelView!!.text = mAccel[0].toString()
                mYAccelView!!.text = mAccel[1].toString()
                mZAccelView!!.text = mAccel[2].toString()
            }
        }
    }

    // De-emphasize transient forces
    private fun lowPass(current: Float, gravity: Float): Float {
        val mAlpha = 0.8f
        return gravity * mAlpha + current * (1 - mAlpha)
    }

    // De-emphasize constant forces
    private fun highPass(current: Float, gravity: Float): Float {
        return current - gravity
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // NA
    }
}