package mobiledev.unb.ca.sqlitetest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView mListView;

    // Cursor query attributes
    private final String[] FROM = {DatabaseHelper.ITEM, DatabaseHelper.NUM};
    private final int[] TO = {R.id.item_textview, R.id.num_textview};

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

        // Create a new DBManager object
        dbManager = new DBManager(this);
        setUpListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    private void setUpListView() {
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
    }

    private void addItem(String item, String num) {
        // Ideally this would be done in a worker thread because
        // getWritableDatabase() can be long running operation
        // Set up the ListView again once we've modified the database
        dbManager.insertRecord(item, num);
        setUpListView();
    }

    private void deleteItem(int id) {
        // Ideally this would be done in a worker thread because
        // getWritableDatabase() can be long running operation
        dbManager.deleteRecord(id);
        setUpListView();
    }
}
