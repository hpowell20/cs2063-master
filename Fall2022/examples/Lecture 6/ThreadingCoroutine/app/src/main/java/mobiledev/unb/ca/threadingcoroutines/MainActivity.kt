package mobiledev.unb.ca.threadingcoroutines

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private var mImageView: ImageView? = null
    private var mProgressBar: ProgressBar? = null

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var loadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        mImageView = findViewById(R.id.imageView)
        mProgressBar = findViewById(R.id.progressBar)

        loadButton = findViewById(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton() }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private fun onClickLoadButton() {
        loadButton.isEnabled = false
        mainActivityViewModel.loadImage(applicationContext, mImageView, mProgressBar)
    }

    private fun onClickOtherButton() {
        Toast.makeText(this@MainActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }
}
