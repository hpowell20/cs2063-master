package mobiledev.unb.ca.roompersistencetest.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mobiledev.unb.ca.roompersistencetest.R;
import mobiledev.unb.ca.roompersistencetest.entity.Item;

public class ItemsAdapter  extends ArrayAdapter<Item> {
    public ItemsAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.item_textview);
        TextView tvNum = convertView.findViewById(R.id.num_textview);

        // Populate the data into the template view using the data object
        tvName.setText(item.getName());
        tvNum.setText(String.valueOf(item.getNum()));

        // Return the completed view to render on screen
        return convertView;
    }}