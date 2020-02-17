package mobiledev.unb.ca.roompersistencetest.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import mobiledev.unb.ca.roompersistencetest.dao.ItemDao;
import mobiledev.unb.ca.roompersistencetest.db.ItemRoomDatabase;
import mobiledev.unb.ca.roompersistencetest.entity.Item;

public class ItemRepository {
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    public LiveData<List<Item>> listAllRecords() {
        return itemDao.listAllRecords();
    }

    public void insertRecord(String name, int num) {
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setNum(num);

        insertRecord(newItem);
    }

    private void insertRecord(final Item item) {
        ItemRoomDatabase.databaseWriterExecutor.execute(() -> {
            itemDao.insert(item);
        });
    }

    public void deleteRecord(final Item item) {
        ItemRoomDatabase.databaseWriterExecutor.execute(() -> {
            itemDao.deleteItem(item);
        });
    }
}
