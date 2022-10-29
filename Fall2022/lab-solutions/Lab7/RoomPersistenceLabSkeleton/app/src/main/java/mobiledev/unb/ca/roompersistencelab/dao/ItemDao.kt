package mobiledev.unb.ca.roompersistencelab.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mobiledev.unb.ca.roompersistencelab.entity.Item

/**
 * This DAO object validates the SQL at compile-time and associates it with a method
 */
@Dao
interface ItemDao {
    // TODO
    //  Add app specific queries in here
    //  Additional details can be found at https://developer.android.com/reference/android/arch/persistence/room/Dao
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Item?)

    @Query("SELECT * FROM item_table WHERE name = :name ORDER BY num")
    fun findItemsWithName(name: String?): List<Item>
}