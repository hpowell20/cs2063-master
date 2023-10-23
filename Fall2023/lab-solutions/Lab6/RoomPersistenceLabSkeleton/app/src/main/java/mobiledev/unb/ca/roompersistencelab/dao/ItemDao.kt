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
    //  Add app specific queries for:
    //   1. Insert new record
    //   2. Query for items by name
    //  Additional details can be found at https://developer.android.com/reference/kotlin/androidx/room/Query
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Item?)

    @Query("SELECT * FROM item_table WHERE name = :name ORDER BY num")
    fun findItemsWithName(name: String?): List<Item>
}
