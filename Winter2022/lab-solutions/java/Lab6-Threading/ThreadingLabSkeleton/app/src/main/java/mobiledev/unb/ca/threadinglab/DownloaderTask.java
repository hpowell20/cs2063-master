package mobiledev.unb.ca.threadinglab;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import mobiledev.unb.ca.threadinglab.model.GeoData;
import mobiledev.unb.ca.threadinglab.util.JsonUtils;

public class DownloaderTask {
    private static final int DOWNLOAD_TIME = 4;      // Download time simulation

    private final Context appContext;

    private final GeoDataListActivity activity;
    private Button refreshButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public DownloaderTask(GeoDataListActivity activity) {
        this.activity = activity;
        appContext = activity.getApplicationContext();
    }

    public DownloaderTask setRefreshButton(Button refreshButton) {
        this.refreshButton = refreshButton;
        return this;
    }

    public DownloaderTask setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    public DownloaderTask setupRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public void execute() {
        // Disable the button
        refreshButton.setEnabled(false);

        // Show the progress bar
        progressBar.setIndeterminate(false);
        progressBar.setProgress(0);
        progressBar.setMax(DOWNLOAD_TIME);
        progressBar.setVisibility(View.VISIBLE);

        // Perform background call to read the information from the URL
        Executors.newSingleThreadExecutor().execute(() -> {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            // Perform the background work
            JsonUtils jsonUtils = new JsonUtils();
            ArrayList<GeoData> mGeoDataList = jsonUtils.getGeoData();
            if (null == mGeoDataList) {
                Toast.makeText(appContext, activity.getString(R.string.download_error_msg), Toast.LENGTH_SHORT).show();
            }

            // Simulating long-running operation
            for (int i = 1; i < DOWNLOAD_TIME; i++) {
                sleep();
                final int step = i;
                mainHandler.post(() -> progressBar.setProgress(step * 10));
            }

            // Update the UI with the results
            mainHandler.post(() -> setUpListView(mGeoDataList));
        });
    }

    private void sleep() {
        try {
            int mDelay = 500;
            Thread.sleep(mDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setUpListView(ArrayList<GeoData> mGeoDataList) {
        //  Now that the download is complete, enable the button again
        refreshButton.setEnabled(true);

        // TODO
        //  Reset the progress bar, and make it disappear
        progressBar.setProgress(0);
        progressBar.setVisibility(View.INVISIBLE);

        // TODO
        //  Setup the RecyclerView
        setupRecyclerView(mGeoDataList);

        // TODO
        //  Create a Toast indicating that the download is complete. Set its text
        //  to be the result String from doInBackground
        Toast.makeText(appContext, activity.getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
    }

    private void setupRecyclerView(List<GeoData> mGeoDataList) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mGeoDataList, activity, activity.isTwoPane()));
    }
}
