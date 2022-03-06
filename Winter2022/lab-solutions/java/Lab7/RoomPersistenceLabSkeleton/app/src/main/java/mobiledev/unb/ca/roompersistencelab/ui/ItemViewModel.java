package mobiledev.unb.ca.roompersistencelab.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import mobiledev.unb.ca.roompersistencelab.entity.Item;
import mobiledev.unb.ca.roompersistencelab.repository.ItemRepository;

public class ItemViewModel extends AndroidViewModel {
    private final ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
    }

    // TODO
    //  Add mapping calls between the UI and Database
    public void insert(String name, int num) {
        itemRepository.insertRecord(name, num);
    }

    public List<Item> findItemsByName(String name) {
        return itemRepository.findItemsWithName(name);
    }
}
