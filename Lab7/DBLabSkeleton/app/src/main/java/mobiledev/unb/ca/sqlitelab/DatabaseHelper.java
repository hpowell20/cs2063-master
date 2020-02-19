package mobiledev.unb.ca.sqlitelab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database information
    private static final String DATABASE_NAME = "SQLITE_LAB.DB";
    private static final int DATABASE_VERSION = 1;

    // Table columns
    public static final String _ID = "_id";
    public static final String ITEM = "item";
    public static final String NUM = "number";
    private static String[] COLUMNS = { _ID, ITEM, NUM };

    // Table creation statement
    public static final String TABLE_NAME = "mytable";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ITEM + " TEXT, " + NUM + " INT);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}

