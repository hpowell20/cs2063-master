package mobiledev.unb.ca.threadinglab

import mobiledev.unb.ca.threadinglab.model.GeoData
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ItemRecyclerViewAdapter(
    private val mValues: List<GeoData>,
    private val parentActivity: AppCompatActivity,
    private val isTwoPane: Boolean
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.geodata_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mGeoData = mValues[position]
        holder.mIdView.text = mValues[position].title
        holder.mView.setOnClickListener {
            /*
               Setting the data to be sent to the Detail portion of the template.
               Here, we send the title, longitude, and latitude of the Earthquake
               that was clicked in the RecyclerView. The Detail Activity/Fragment
               will then display this information. Condition check is whether we
               are twoPane on a Tablet, which varies how we pass arguments to the
               participating activity/fragment.
             */
            val title = holder.mGeoData!!.title
            val lng = holder.mGeoData!!.longitude
            val lat = holder.mGeoData!!.latitude
            if (isTwoPane) {
                val arguments = Bundle()
                arguments.putString(GeoDataDetailFragment.TITLE, title)
                arguments.putString(GeoDataDetailFragment.LNG, lng)
                arguments.putString(GeoDataDetailFragment.LAT, lat)
                val fragment = GeoDataDetailFragment()
                fragment.arguments = arguments
                parentActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.geodata_detail_container, fragment)
                    .commit()
            } else {
                // TODO
                //  Create an Intent to start GeoDataDetailFragment from the parentActivity class.
                //  You'll need to add some extras to this intent. Look at that class, and the
                //  example Fragment transaction for the two pane case above, to
                //  figure out what you need to add.
            }
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(
        mView) {
        val mIdView: TextView = mView.findViewById(R.id.id)
        private val mContentView: TextView = mView.findViewById(R.id.content)
        var mGeoData: GeoData? = null

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}