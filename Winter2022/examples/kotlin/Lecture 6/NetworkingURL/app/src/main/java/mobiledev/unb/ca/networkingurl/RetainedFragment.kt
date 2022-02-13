package mobiledev.unb.ca.networkingurl

import android.app.Fragment
import android.content.Context
import mobiledev.unb.ca.networkingurl.RetainedFragment.OnFragmentInteractionListener
import android.os.Bundle
import android.util.Log
import mobiledev.unb.ca.networkingurl.HttpGetTask
import java.lang.RuntimeException

class RetainedFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun onButtonPressed() {
        HttpGetTask(this).execute()
    }

    fun onDownloadFinished(result: String?) {
        if (null != mListener) {
            mListener!!.onDownloadFinished(result)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentInteractionListener) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onDownloadFinished(result: String?)
    }

    companion object {
        const val TAG = "RetainedFragment"
    }
}