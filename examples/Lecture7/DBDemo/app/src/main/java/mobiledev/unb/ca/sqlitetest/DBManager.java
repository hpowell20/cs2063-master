package mobiledev.unb.ca.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor listAllRecords() {
        Cursor cursor = openReadOnlyDatabase().query(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMNS, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insertRecord(String item, String num) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ITEM, item);
        contentValue.put(DatabaseHelper.NUM, num);
        openWriteDatabase().insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public void deleteRecord(int id) {
        openWriteDatabase().delete(DatabaseHelper.TABLE_NAME,
                DatabaseHelper._ID + "=?",
                new String[]{String.valueOf(id)});
    }

    private SQLiteDatabase openReadOnlyDatabase() {
        return dbHelper.getReadableDatabase();
    }

    private SQLiteDatabase openWriteDatabase() {
        return dbHelper.getWritableDatabase();
    }
}
