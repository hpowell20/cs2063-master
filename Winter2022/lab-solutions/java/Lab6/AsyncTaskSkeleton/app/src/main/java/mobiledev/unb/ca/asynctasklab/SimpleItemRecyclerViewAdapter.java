package mobiledev.unb.ca.asynctasklab;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

import mobiledev.unb.ca.asynctasklab.model.GeoData;

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final static String TAG = "SimpleItemRecyclerView";

    private final List<GeoData> mValues;
    private final WeakReference<GeoDataListActivity> mActivity;

    public SimpleItemRecyclerViewAdapter(List<GeoData> data, WeakReference<GeoDataListActivity> mActivity) {
        mValues = data;
        this.mActivity = mActivity;
    }

    @NonNull
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

            final GeoDataListActivity activity = mActivity.get();
            if (activity != null) {
                if (activity.isTwoPane()) {
                    Bundle arguments = new Bundle();
                    arguments.putString(GeoDataDetailFragment.TITLE, title);
                    arguments.putString(GeoDataDetailFragment.LNG, lng);
                    arguments.putString(GeoDataDetailFragment.LAT, lat);
                    GeoDataDetailFragment fragment = new GeoDataDetailFragment();
                    fragment.setArguments(arguments);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.geodata_detail_container, fragment)
                            .commit();
                } else {
                    // TODO
                    //  Create an Intent to start GeoDataDetailActivity. You'll need
                    //  to add some extras to this intent. Look at that class, and the
                    //  example Fragment transaction for the two pane case above, to
                    //  figure out what you need to add.
                    Intent intent = new Intent(activity, GeoDataDetailActivity.class);
                    intent.putExtra(GeoDataDetailFragment.TITLE, title);
                    intent.putExtra(GeoDataDetailFragment.LNG, lng);
                    intent.putExtra(GeoDataDetailFragment.LAT, lat);

                    try {
                        activity.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Log.e(TAG, "Unable to start activity", e);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}