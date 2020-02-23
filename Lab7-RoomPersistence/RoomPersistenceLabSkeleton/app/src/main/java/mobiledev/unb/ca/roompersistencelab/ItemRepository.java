package mobiledev.unb.ca.roompersistencelab;

import android.app.Application;

public class ItemRepository {
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    // TODO Insert app related queries
}
