package mobiledev.unb.ca.roompersistencelab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;
    private LiveData<List<Item>> items;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
    }

    public LiveData<List<Item>> getItems() {
        if (null == items){
            items = updateItemsList();
        }
        return items;
    }

    public LiveData<List<Item>> updateItemsList(){
        // TODO Remove this return statement when you have completed this part
        return null;
    }

    // TODO Add mapping calls between the UI and Database
    public void insert(String name, int num) {
        itemRepository.insertRecord(name, num);
    }
}
