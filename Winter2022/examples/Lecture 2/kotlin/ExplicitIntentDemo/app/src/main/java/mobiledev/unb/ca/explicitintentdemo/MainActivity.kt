package mobiledev.unb.ca.explicitintentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mobiledev.unb.ca.explicitintentdemo.R
import android.content.Intent
import android.view.View
import android.widget.Button
import mobiledev.unb.ca.explicitintentdemo.ActivityTwo

class MainActivity : AppCompatActivity() {
    private var mButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mButton = findViewById<View>(R.id.button) as Button
        mButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityTwo::class.java)
            startActivity(intent)
        }
    }
}