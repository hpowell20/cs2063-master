package mobiledev.unb.ca.asynctasklab;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

import mobiledev.unb.ca.asynctasklab.model.GeoData;
import mobiledev.unb.ca.asynctasklab.util.JsonUtils;

public class DownloaderTask extends AsyncTask<String, Integer, String> {
    private static final int DOWNLOAD_TIME = 4;      // Download time simulation

    private List<GeoData> mGeoDataList;

    // Keep a weak reference to the activity
    /*
    If, however, the AsyncTask is not an inner class of the Activity, you will need to pass an
    Activity reference to the AsyncTask.
    When you do this, one potential problem that may occur is
    that the AsyncTask will keep the reference of the Activity until the AsyncTask has
    completed its work in its background thread.
    If the Activity is finished or killed before the AsyncTask's
    background thread work is done, the AsyncTask will still
    have its reference to the Activity, and therefore it cannot be garbage collected.

    As a result, this will cause a memory leak.  In order to prevent this from happening,
    make use of a WeakReference in the AsyncTask instead of having a direct
    reference to the Activity.
     */
    private final WeakReference<GeoDataListActivity> mActivity;

    public DownloaderTask(GeoDataListActivity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        /* Anything executed in here will be done on the UI thread before
           the doInBackground method is executed. This allows some prep work
           to be completed on the UI thread before the new thread activates.
        */
        final Activity activity = mActivity.get();
        if (activity != null) {
            // TODO
            //  Disable the button so it can't be clicked again once a download has been started
            //  Hint: Button is subclass of TextView. Read this document to see how to disable it.
            //  http://developer.android.com/reference/android/widget/TextView.html
            activity.findViewById(R.id.button).setEnabled(false);

            // TODO
            //  Set the progress bar's maximum to be DOWNLOAD_TIME, its initial progress to be
            //  0, and also make sure it's visible.
            //  Hint: Read the documentation on ProgressBar
            //  http://developer.android.com/reference/android/widget/ProgressBar.html
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            progressBar.setIndeterminate(false);
            progressBar.setProgress(0);
            progressBar.setMax(DOWNLOAD_TIME);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String successMessage = params[0];
        String errorMessage = params[1];

        // TODO
        //  Create an instance of JsonUtils and get the data from it,
        //  store the data in mGeoDataList
        JsonUtils jsonUtils = new JsonUtils();
        mGeoDataList = jsonUtils.getGeoData();
        if (null == mGeoDataList) {
            return errorMessage;
        }

        // Leave this while loop here to simulate a lengthy download
        for(int i = 0; i < DOWNLOAD_TIME; i++) {
            try {
                Thread.sleep(1000);
                // TODO
                //  Update the progress bar; calculate an appropriate value for
                //  the new progress using i
                publishProgress(i * 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return successMessage;
    }

    /** Once the DownloaderTask completes, hide the progress bar and update the
     *  RecyclerView with the geographic earthquake data we simulated downloading.
     */
    @Override
    protected void onPostExecute(String result) {
        final Activity activity = mActivity.get();
        if (activity != null) {
            super.onPostExecute(result);

            // TODO
            //  Now that the download is complete, enable the button again
            activity.findViewById(R.id.button).setEnabled(true);

            // TODO
            //  Reset the progress bar, and make it disappear
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);

            // TODO
            //  Setup the RecyclerView
            RecyclerView recyclerView = activity.findViewById(R.id.geodata_list);
            setupRecyclerView(recyclerView);

            // TODO
            //  Create a Toast indicating that the download is complete. Set its text
            //  to be the result String from doInBackground
            Toast.makeText(activity.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }

    /** Handle mProgressBar display updates whenever the AsyncTask subclass
     * DownloaderTask notifies its onProgressUpdate()
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        final Activity activity = mActivity.get();
        if (activity != null) {
            // TODO
            //  Update the progress bar using values
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mGeoDataList, mActivity));
    }
}