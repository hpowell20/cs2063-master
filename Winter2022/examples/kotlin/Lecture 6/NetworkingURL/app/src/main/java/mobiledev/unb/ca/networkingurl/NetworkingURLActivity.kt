package mobiledev.unb.ca.networkingurl

import android.app.Activity
import mobiledev.unb.ca.networkingurl.RetainedFragment.OnFragmentInteractionListener
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import android.widget.Button

class NetworkingURLActivity : Activity(), OnFragmentInteractionListener {
    private var textView: TextView? = null
    private var retainedFragment: RetainedFragment? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        textView = findViewById(R.id.textView)

        val loadButton = findViewById<Button>(R.id.load_button)
        loadButton.setOnClickListener { onClick() }

        if (null != savedInstanceState) {
            retainedFragment = fragmentManager
                .findFragmentByTag(RetainedFragment.TAG) as RetainedFragment
            textView!!.text = savedInstanceState.getCharSequence(TEST_VIEW_KEY)
        } else {
            retainedFragment = RetainedFragment()
            fragmentManager.beginTransaction()
                .add(retainedFragment, RetainedFragment.TAG)
                .commit()
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