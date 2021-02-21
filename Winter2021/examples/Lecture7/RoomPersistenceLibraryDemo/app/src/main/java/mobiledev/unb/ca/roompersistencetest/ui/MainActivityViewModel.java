package mobiledev.unb.ca.roompersistencetest.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import mobiledev.unb.ca.roompersistencetest.repository.ItemRepository;
import mobiledev.unb.ca.roompersistencetest.entity.Item;

public class MainActivityViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;
    private LiveData<List<Item>> items;

    public LiveData<List<Item>> getItems() {
        if (null == items){
            items = updateItemsList();
        }
        return items;
    }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        items = updateItemsList();
    }

    public LiveData<List<Item>> updateItemsList(){
        return itemRepository.listAllRecords();
    }

    public void insert(String name, int num) {
        itemRepository.insertRecord(name, num);
    }

    public void delete(Item item) {
        itemRepository.deleteRecord(item);
    }
}

