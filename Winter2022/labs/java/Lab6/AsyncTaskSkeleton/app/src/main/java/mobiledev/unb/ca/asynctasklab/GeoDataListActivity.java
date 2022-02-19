package mobiledev.unb.ca.asynctasklab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import mobiledev.unb.ca.asynctasklab.model.GeoData;

/**
 * An activity representing a list of GeoData. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link GeoDataDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class GeoDataListActivity extends AppCompatActivity {
    private List<GeoData> mGeoDataList;
    private Button mBgButton;

    // Indicator if the activity is in two-pane mode (for example: running on a tablet)
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_geodata_list);

        mBgButton = findViewById(R.id.button);

        // TODO
        //  Create a variable used as a reference for the progress bar to use in the AsyncTask class
        //  NOTE: Look for the ID of the progress bar in the resource files

        // HINT:
        // Nothing to do here, just note that you will be completing the downloadGeoData()
        // function. It will set mGeoDataList to contain the downloaded geo data.
        downloadGeoData();

        // Test if we're on a tablet
        if(findViewById(R.id.geodata_detail_container) != null) {
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

        mBgButton.setOnClickListener(view -> {
            // Update the geo data
            downloadGeoData();
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "I'm working!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        if (findViewById(R.id.geodata_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    /**
     *  Setting up of RecyclerView with relevant data
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mGeoDataList));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<GeoData> mValues;

        public SimpleItemRecyclerViewAdapter(List<GeoData> data) {
            mValues = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.geodata_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mGeoData = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getTitle());

            holder.mView.setOnClickListener(v -> {
                /*
                  Setting the data to be sent to the Detail portion of the template.
                  Here, we send the title, longitude, and latitude of the Earthquake
                  that was clicked in the RecyclerView. The Detail Activity/Fragment
                  will then display this information. Condition check is whether we
                  are twoPane on a Tablet, which varies how we pass arguments to the
                  participating activity/fragment.
                 */
                String title = holder.mGeoData.getTitle();
                String lng = holder.mGeoData.getLongitude();
                String lat = holder.mGeoData.getLatitude();

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(GeoDataDetailFragment.TITLE, title);
                    arguments.putString(GeoDataDetailFragment.LNG, lng);
                    arguments.putString(GeoDataDetailFragment.LAT, lat);
                    GeoDataDetailFragment fragment = new GeoDataDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.geodata_detail_container, fragment)
                            .commit();
                } else {
                    // TODO
                    //  Create an Intent to start GeoDataDetailActivity. You'll need
                    //  to add some extras to this intent. Look at that class, and the
                    //  example Fragment transaction for the two pane case above, to
                    //  figure out what you need to add.

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public GeoData mGeoData;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = view.findViewById(R.id.id);
                mContentView = view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    private void downloadGeoData() {
        // TODO
        //  Check whether there is a network connection. If there is, Create a DownLoaderTask
        //  and execute it. If there isn't, create a Toast indicating that there is no network
        //  connection.
        //  Hint: Read this for help on checking network connectivity:
        //  https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
        //  Hint: Read this for help with Toast:
        //  http://developer.android.com/guide/topics/ui/notifiers/toasts.html
    }
}
