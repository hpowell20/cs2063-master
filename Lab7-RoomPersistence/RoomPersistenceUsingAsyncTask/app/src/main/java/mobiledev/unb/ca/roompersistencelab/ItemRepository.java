package mobiledev.unb.ca.roompersistencelab;

import android.app.Application;

public class ItemRepository {
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    // TODO Add query specific methods
    //  Make the calls directly to the DAO object; the AsyncTask classes from MainActivity
    //  will handle the threading from the UI
}
