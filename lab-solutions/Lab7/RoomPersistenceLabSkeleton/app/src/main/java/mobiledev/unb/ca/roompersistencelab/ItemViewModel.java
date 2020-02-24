package mobiledev.unb.ca.roompersistencelab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
    }

    // TODO Add mapping calls between the UI and Database
    public void insert(String name, int num) {
        itemRepository.insertRecord(name, num);
    }

    public List<Item> findItemsByName(String name) {
        return itemRepository.findItemsWithName(name);
    }
}
