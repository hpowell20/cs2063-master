package course.examples.networking.url;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NetworkingURLActivity extends Activity implements RetainedFragment.OnFragmentInteractionListener{
	private TextView mTextView;
    private RetainedFragment mRetainedFragment;
    private final static String MTEXTVIEW_TEXT_KEY = "MTEXTVIEW_TEXT_KEY";

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mTextView = findViewById(R.id.textView);

        if (null != savedInstanceState) {
            mRetainedFragment = (RetainedFragment) getFragmentManager()
                    .findFragmentByTag(RetainedFragment.TAG);
            mTextView.setText(savedInstanceState.getCharSequence(MTEXTVIEW_TEXT_KEY));
        } else {
            mRetainedFragment = new RetainedFragment();
            getFragmentManager().beginTransaction()
                    .add(mRetainedFragment, RetainedFragment.TAG)
                    .commit();
        }
    }

	public void onClick(View v) {
        mRetainedFragment.onButtonPressed();
	}

    @Override
    public void onDownloadfinished(String result) {
        mTextView.setText(result);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(MTEXTVIEW_TEXT_KEY,mTextView.getText());
    }
}
