package mobiledev.unb.ca.roompersistencetest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mobiledev.unb.ca.roompersistencetest.entity.Item;
import mobiledev.unb.ca.roompersistencetest.ui.ItemsAdapter;
import mobiledev.unb.ca.roompersistencetest.ui.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private MainActivityViewModel mainActivityViewModel;
    private ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the listener events
        mListView = findViewById(R.id.listview);
        mListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Item item = mainActivityViewModel.getItems().getValue().get(position);
            deleteItem(item);
            return true;
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_layout, null);
            builder.setView(dialogView)
                    .setPositiveButton(getString(R.string.dialog_button_add), (dialog, id) -> {
                        EditText itemEditText = dialogView.findViewById(R.id.item_edit_text);
                        EditText numEditText = dialogView.findViewById(R.id.number_edit_text);
                        String item = itemEditText.getText().toString();
                        String num = numEditText.getText().toString();
                        addItem(item, num);
                    })
                    .setNegativeButton(getString(R.string.dialog_button_cancel), (dialog, id) -> {
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            alertDialog.show();
        });

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getItems().observe(this, items -> {
            if(items != null) {
                itemsAdapter = new ItemsAdapter(getApplicationContext(), items);
                mListView.setAdapter(itemsAdapter);
            }
            itemsAdapter.notifyDataSetChanged();
        });
    }

    private void addItem(String item, String num) {
        mainActivityViewModel.insert(item, Integer.parseInt(num));
    }

    private void deleteItem(Item item) {
        mainActivityViewModel.delete(item);
    }
}
