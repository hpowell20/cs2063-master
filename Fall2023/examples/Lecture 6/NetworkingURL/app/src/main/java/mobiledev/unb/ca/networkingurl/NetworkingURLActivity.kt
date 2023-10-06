package mobiledev.unb.ca.networkingurl

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import mobiledev.unb.ca.networkingurl.RetainedFragment.OnFragmentInteractionListener

class NetworkingURLActivity : AppCompatActivity(), OnFragmentInteractionListener {
    private var textView: TextView? = null
    private var retainedFragment: RetainedFragment? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        textView = findViewById(R.id.textView)

        val loadButton = findViewById<Button>(R.id.load_button)
        loadButton.setOnClickListener { onClick() }

        if (null != savedInstanceState) {
            retainedFragment = supportFragmentManager.findFragmentByTag(RetainedFragment.TAG) as RetainedFragment
            textView!!.text = savedInstanceState.getCharSequence(TEST_VIEW_KEY)
        } else {
            retainedFragment = RetainedFragment()
            supportFragmentManager.beginTransaction().add(retainedFragment!!, RetainedFragment.TAG).commit()
        }
    }

    fun onClick() {
        retainedFragment!!.onButtonPressed()
    }

    override fun onDownloadFinished(result: String?) {
        textView!!.text = result
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(TEST_VIEW_KEY, textView!!.text)
    }

    companion object {
        private const val TEST_VIEW_KEY = "TEST_VIEW_KEY"
    }
}