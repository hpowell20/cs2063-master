package mobiledev.unb.ca.roompersistencetest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

    //private DBManager dbManager;
    //private ItemRepository itemRepository;
    private ListView mListView;

    MainActivityViewModel mainActivityViewModel;
    ItemsAdapter itemsAdapter;

    // Cursor query attributes
    private final String[] FROM = { DatabaseHelper.ITEM, DatabaseHelper.NUM };
    private final int[] TO = { R.id.item_textview, R.id.num_textview};

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
                //String stringId = String.valueOf(id);
                //deleteItem(stringId);
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

        // Create a new DatabaseHelper object
        /*dbManager = new DBManager(this);
        dbManager.open();
        setUpListView();*/
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }*/

    /*private void setUpListView() {
        // Ideally this would be done in a worker thread because
        // the list items operation could be long running operation
        Cursor cursor = dbManager.listAllRecords();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_layout,
                cursor,
                FROM,
                TO,
                0);
        adapter.notifyDataSetChanged();
        mListView.setAdapter(adapter);
    }*/

    private void addItem(String item, String num) {
        mainActivityViewModel.insert(item, Integer.parseInt(num));
        //dbManager.insertRecord(item, num);
        // Ideally this would be done in a worker thread because
        // getWritableDatabase() can be long running operation
        // Set up the ListView again once we've modified the database
        //setUpListView();
    }

    private void deleteItem(int id) {
        mainActivityViewModel.delete(id);
        // Ideally this would be done in a worker thread because
        // getWritableDatabase() can be long running operation
        //dbManager.deleteRecord(item);
        //setUpListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
