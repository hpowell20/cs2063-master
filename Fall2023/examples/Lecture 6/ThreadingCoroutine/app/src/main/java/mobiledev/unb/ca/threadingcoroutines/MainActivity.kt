package mobiledev.unb.ca.threadingcoroutines

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var loadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        loadButton = findViewById(R.id.loadButton)
        loadButton.setOnClickListener { onClickLoadButton() }

        val otherButton = findViewById<Button>(R.id.otherButton)
        otherButton.setOnClickListener { onClickOtherButton() }
    }

//    private fun onClickLoadButton() {
//        loadButton.isEnabled = false
//        val mainActivityViewModel: MainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
//        mainActivityViewModel.loadImage(applicationContext,
//            findViewById(R.id.imageView),
//            findViewById(R.id.progressBar))
//    }

    private fun onClickLoadButton() {
        loadButton.isEnabled = false
        val loadIconTask: LoadIconTask = LoadIconTask(this)
            .setImageView(findViewById(R.id.imageView))
            .setProgressBar(findViewById(R.id.progressBar))
        loadIconTask.execute()
    }
    private fun onClickOtherButton() {
        Toast.makeText(this@MainActivity, "I'm Working",
            Toast.LENGTH_SHORT).show()
    }
}
