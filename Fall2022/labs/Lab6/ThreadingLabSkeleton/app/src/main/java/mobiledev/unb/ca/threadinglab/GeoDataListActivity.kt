package mobiledev.unb.ca.threadinglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * An activity representing a list of GeoData. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [GeoDataDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class GeoDataListActivity : AppCompatActivity() {
    // Indicator if the activity is in two-pane mode (for example: running on a tablet)
    var isTwoPane = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentManager = supportFragmentManager
        setContentView(R.layout.activity_geodata_list)

        // Test if we're on a tablet
        if (findViewById<View?>(R.id.geodata_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            isTwoPane = true

            // Create a new detail fragment if one does not exist
            var geoDataDetailFragment =
                fragmentManager.findFragmentByTag("Detail") as GeoDataDetailFragment?
            if (geoDataDetailFragment == null) {
                // Init new detail fragment
                geoDataDetailFragment = GeoDataDetailFragment()
                val args = Bundle()
                geoDataDetailFragment.arguments = args
                fragmentManager.beginTransaction()
                    .replace(R.id.geodata_detail_container, geoDataDetailFragment, "Detail")
                    .commit()
            }
        }
        val mBgButton = findViewById<Button>(R.id.button)
        mBgButton.setOnClickListener {
            // Update the geo data
            downloadGeoData()
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view: View? ->
            Snackbar.make(
                view!!, "I'm working!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun downloadGeoData() {
        // TODO
        //  Check whether there is a network connection. 
        //  If there is, Create a DownLoaderTask using this activity in the constructor
        //  and use the setters to pass in the objects needed during execution
        //  If there isn't, create a Toast indicating that there is no network connection.
        //  Hint: Read this for help on checking network connectivity:
        //  https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
        //  Hint: Read this for help with Toast:
        //  http://developer.android.com/guide/topics/ui/notifiers/toasts.html
    }
}