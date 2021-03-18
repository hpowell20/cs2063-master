package mobiledev.unb.ca.roompersistencelab.repository;

import android.app.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import mobiledev.unb.ca.roompersistencelab.dao.ItemDao;
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase;
import mobiledev.unb.ca.roompersistencelab.entity.Item;

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
    public void insertRecord(String name, int num) {
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setNum(num);

        insertRecord(newItem);
    }

    private void insertRecord(final Item item) {
        AppDatabase.databaseWriterExecutor.execute(() -> itemDao.insert(item));
    }

    public List<Item> findItemsWithName(final String name) {
        Future<List<Item>> future = AppDatabase.databaseWriterExecutor.submit(() -> itemDao.findItemsWithName(name));

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
