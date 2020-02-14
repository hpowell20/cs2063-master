package mobiledev.unb.ca.roompersistencetest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                deleteItem((int)id);
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                builder.setView(dialogView)
                        .setPositiveButton(getString(R.string.dialog_button_add), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText itemEditText = dialogView.findViewById(R.id.item_edit_text);
                                EditText numEditText = dialogView.findViewById(R.id.number_edit_text);
                                String item = itemEditText.getText().toString();
                                String num = numEditText.getText().toString();
                                addItem(item, num);
                            }
                        })
                        .setNegativeButton(getString(R.string.dialog_button_cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alertDialog.show();
            }
        });

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                if(items != null) {
                    itemsAdapter = new ItemsAdapter(getApplicationContext(), items);
                    mListView.setAdapter(itemsAdapter);
                }
                itemsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addItem(String item, String num) {
        mainActivityViewModel.insert(item, Integer.parseInt(num));
    }

    private void deleteItem(int id) {
        mainActivityViewModel.delete(id);
    }
}
