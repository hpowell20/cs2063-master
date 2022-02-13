package mobiledev.unb.ca.networkingurl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NetworkingURLActivity extends Activity implements RetainedFragment.OnFragmentInteractionListener{
    private final static String TEST_VIEW_KEY = "TEST_VIEW_KEY";

    private TextView textView;
    private RetainedFragment retainedFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        textView = findViewById(R.id.textView);

        if (null != savedInstanceState) {
            retainedFragment = (RetainedFragment) getFragmentManager()
                    .findFragmentByTag(RetainedFragment.TAG);
            textView.setText(savedInstanceState.getCharSequence(TEST_VIEW_KEY));
        } else {
            retainedFragment = new RetainedFragment();
            getFragmentManager().beginTransaction()
                    .add(retainedFragment, RetainedFragment.TAG)
                    .commit();
        }
    }

	public void onClick(View v) {
	    retainedFragment.onButtonPressed();
	}

    @Override
    public void onDownloadFinished(String result) {
        textView.setText(result);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(TEST_VIEW_KEY, textView.getText());
    }
}
