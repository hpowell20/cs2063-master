package mobiledev.unb.ca.roompersistencelab.repository;

import android.app.Application;

import mobiledev.unb.ca.roompersistencelab.dao.ItemDao;
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase;

public class ItemRepository {
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    //  See the example project file at
    //  https://github.com/hpowell20/cs2063-winter-2021-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //  to see examples of how to work with the Executor Service

    // TODO Add query specific methods
    //  HINTS
    //   The insert operation needs to make use of the Runnable interface
    //   The search operation needs to make use of the Callable interface
}
