package mobiledev.unb.ca.roompersistencelab;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ItemRepository {
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    // TODO Add query specific methods
    //  See the example project file at
    //  https://github.com/hpowell20/cs2063-winter-2020-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //  to see examples of how to work with the Executor Service

    // HINTS
    //  The insert operation needs to make use of the Runnable interface
    //  The search operation needs to make use of the Callable interface

    public void insertRecord(String name, int num) {
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setNum(num);

        insertRecord(newItem);
    }

    private void insertRecord(final Item item) {
        AppDatabase.databaseWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                itemDao.insert(item);
            }
        });
    }

    /*public LiveData<List<Item>> findItemsWithName(String name) {
        return itemDao.findItemsWithName(name);
    }*/

    public List<Item> findItemsWithName(final String name) {
        Future<List<Item>> future = AppDatabase.databaseWriterExecutor.submit(new Callable<List<Item>>(){
            public List<Item> call() {
                return itemDao.findItemsWithName(name);
            }
        });

        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
