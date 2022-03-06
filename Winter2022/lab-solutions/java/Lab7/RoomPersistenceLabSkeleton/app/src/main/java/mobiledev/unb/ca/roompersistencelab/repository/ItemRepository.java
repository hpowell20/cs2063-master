package mobiledev.unb.ca.roompersistencelab.repository;

import android.app.Application;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import mobiledev.unb.ca.roompersistencelab.dao.ItemDao;
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase;
import mobiledev.unb.ca.roompersistencelab.entity.Item;

public class ItemRepository {
    private final ItemDao itemDao;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    //  See the example project file at
    //  https://github.com/hpowell20/cs2063-winter-2022-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //  to see examples of how to work with the Executor Service

    // TODO Add query specific methods
    //  HINTS
    //   The insert operation can make use of the Runnable interface
    //   As the search operation needs to return results have a look at using Callable interface with Future
    //  Some more details on runnables and callables can be found <a href="https://www.baeldung.com/java-runnable-callable">here</a>

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
        Future<List<Item>> dataReadFuture = AppDatabase.databaseWriterExecutor.submit(() -> itemDao.findItemsWithName(name));

        try {
            while (!dataReadFuture.isDone()) {
                // Simulating another task
                TimeUnit.SECONDS.sleep(1);
            }

            return dataReadFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
