package mobiledev.unb.ca.sqlitelab;


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

    public Cursor listAllRecordsByItem(String item) {
        Cursor cursor = openReadOnlyDatabase().query(DatabaseHelper.TABLE_NAME,
                DatabaseHelper.COLUMNS, "item=?", new String[]{item},
                null, null, DatabaseHelper.NUM + " ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void insertRecord(String item, int num) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ITEM, item);
        contentValue.put(DatabaseHelper.NUM, num);
        openWriteDatabase().insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    private SQLiteDatabase openReadOnlyDatabase() {
        return dbHelper.getReadableDatabase();
    }

    private SQLiteDatabase openWriteDatabase() {
        return dbHelper.getWritableDatabase();
    }
}
