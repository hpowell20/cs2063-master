package mobiledev.unb.ca.asynctasklab;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class DownloaderTask extends AsyncTask<Void, Integer, String> {
    private static final int DOWNLOAD_TIME = 4;      // Download time simulation

    private final WeakReference<Activity> mActivity;

    public DownloaderTask(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    // Keep a reference to the progress bar and buttons so we can interact with it later
//        private final WeakReference<ProgressBar> progressBar;
//        private final WeakReference<Button> backgroundButton;
//
//        // Set the localized download complete message
//        private final String downloadCompleteMessage;
//
//        public DownloaderTask(ProgressBar progressBar, Button backgroundButton, String downloadCompleteMessage) {
//            this.progressBar = new WeakReference<>(progressBar);
//            this.backgroundButton = new WeakReference<>(backgroundButton);
//            this.downloadCompleteMessage = downloadCompleteMessage;
//        }

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


            // TODO
            //  Set the progress bar's maximum to be DOWNLOAD_TIME, its initial progress to be
            //  0, and also make sure it's visible.
            //  Hint: Read the documentation on ProgressBar
            //  http://developer.android.com/reference/android/widget/ProgressBar.html
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO
        //  Create an instance of JsonUtils and get the data from it,
        //  store the data in mGeoDataList

        // Leave this while loop here to simulate a lengthy download
        for(int i = 0; i < DOWNLOAD_TIME; i++) {
            try {
                Thread.sleep(1000);
                // TODO
                //  Update the progress bar; calculate an appropriate value for
                //  the new progress using i

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "Done";
        // downloadCompleteMessage;
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

            // TODO
            //  Reset the progress bar, and make it disappear

            // TODO
            //  Setup the RecyclerView

            // TODO
            //  Create a Toast indicating that the download is complete. Set its text
            //  to be the result String from doInBackground
        }
    }

    /** Handle mProgressBar display updates whenever the AsyncTask subclass
     * DownloaderTask notifies its onProgressUpdate()
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO
        //  Update the progress bar using values
    }
}