package mobiledev.unb.ca.roompersistencetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertRecord(String item, String num) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ITEM, item);
        contentValue.put(DatabaseHelper.NUM, num);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor listAllRecords() {
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMNS, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deleteRecord(String item) {
        database.delete(DatabaseHelper.TABLE_NAME,
                DatabaseHelper._ID + "=?",
                new String[]{item});
    }
}
