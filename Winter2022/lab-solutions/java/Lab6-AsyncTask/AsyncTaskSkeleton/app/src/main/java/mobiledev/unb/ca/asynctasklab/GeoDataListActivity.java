package mobiledev.unb.ca.asynctasklab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * An activity representing a list of GeoData. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GeoDataDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class GeoDataListActivity extends AppCompatActivity {

    // Indicator if the activity is in two-pane mode (for example: running on a tablet)
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_geodata_list);

        // Test if we're on a tablet
        if (findViewById(R.id.geodata_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // Create a new detail fragment if one does not exist
            GeoDataDetailFragment geoDataDetailFragment = (GeoDataDetailFragment) fragmentManager.findFragmentByTag("Detail");
            if (geoDataDetailFragment == null) {
                // Init new detail fragment
                geoDataDetailFragment = new GeoDataDetailFragment();
                Bundle args = new Bundle();

                geoDataDetailFragment.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.geodata_detail_container, geoDataDetailFragment, "Detail").commit();
            }
        }

        Button mBgButton = findViewById(R.id.button);
        mBgButton.setOnClickListener(view -> {
            // Update the geo data
            downloadGeoData();
        });
    }

    public boolean isTwoPane() {
        return mTwoPane;
    }

    private void downloadGeoData() {
        // TODO
        //  Check whether there is a network connection. If there is, Create a DownLoaderTask using
        //  this activity in the constructor and execute it by adding in parameters for success and error messages
        //  URL for reference?
        //  If there isn't, create a Toast indicating that there is no network connection.
        //  Hint: Read this for help on checking network connectivity:
        //  https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
        //  Hint: Read this for help with Toast:
        //  http://developer.android.com/guide/topics/ui/notifiers/toasts.html
        if (isNetworkAvailable()) {
            DownloaderTask downloaderTask = new DownloaderTask(this);
            downloaderTask.execute(getString(R.string.download_complete),
                    getString(R.string.download_error_msg));
        } else {
            // Set Toast message
            Toast.makeText(getApplicationContext(), getString(R.string.no_network_msg), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return null != activeNetworkInfo && activeNetworkInfo.isConnected();
    }
}
