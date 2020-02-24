package mobiledev.unb.ca.roompersistencelab;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * This DAO object validates the SQL at compile-time and associates it with a method
 */
@Dao
public interface ItemDao {
    // TODO Add app specific queries in here
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Query("SELECT * FROM item_table WHERE name = :name ORDER BY num")
    List<Item> findItemsWithName(String name);

    //@Query("SELECT * FROM item_table WHERE name = :name ORDER BY num")
    //LiveData<List<Item>> findItemsWithName(String name);
}
