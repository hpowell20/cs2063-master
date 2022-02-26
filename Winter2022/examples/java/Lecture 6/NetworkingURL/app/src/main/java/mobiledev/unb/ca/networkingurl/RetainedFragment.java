package mobiledev.unb.ca.networkingurl;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

public class RetainedFragment extends Fragment {
    static final String TAG = "RetainedFragment";
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void onButtonPressed() {
        new HttpGetTask(this).execute();
    }

    void onDownloadFinished(String result) {
        if (null != mListener) {
            mListener.onDownloadFinished(result);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onDownloadFinished(String result);
    }
}
