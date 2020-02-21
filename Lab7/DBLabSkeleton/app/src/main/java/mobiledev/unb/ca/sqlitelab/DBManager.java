package mobiledev.unb.ca.sqlitelab;


import android.content.Context;


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

}
