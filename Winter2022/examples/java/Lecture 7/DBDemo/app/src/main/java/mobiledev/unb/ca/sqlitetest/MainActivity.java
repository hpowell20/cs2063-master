package mobiledev.unb.ca.sqlitetest;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;
    private ListView mListView;

    private ExecutorService executor;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the executor service
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the listener events
        mListView = findViewById(R.id.listview);
        mListView.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteItem((int) id);
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
        // Cursor query attributes
        final String[] FROM = {DatabaseHelper.ITEM, DatabaseHelper.NUM};
        final int[] TO = {R.id.item_textview, R.id.num_textview};

        executor.execute(() -> {
            // Perform background call to retrieve the records
            Cursor cursor = dbManager.listAllRecords();

            handler.post(() -> {
                // Update the UI with the results
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                        R.layout.list_layout,
                        cursor,
                        FROM,
                        TO,
                        0);
                adapter.notifyDataSetChanged();
                mListView.setAdapter(adapter);
            });
        });
    }

    private void addItem(String item, String num) {
        executor.execute(() -> {
            // Perform background call to save the record
            dbManager.insertRecord(item, num);

            // Update the UI with the results
            handler.post(this::setUpListView);
        });
    }

    private void deleteItem(int id) {
        executor.execute(() -> {
            // Perform background call to remove the record
            dbManager.deleteRecord(id);

            // Update the UI with the results
            handler.post(this::setUpListView);
        });
    }
}
