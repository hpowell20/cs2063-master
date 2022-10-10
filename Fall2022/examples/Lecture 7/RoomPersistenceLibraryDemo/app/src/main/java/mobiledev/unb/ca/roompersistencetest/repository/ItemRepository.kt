package mobiledev.unb.ca.roompersistencetest.repository

import android.app.Application
import androidx.lifecycle.LiveData
import mobiledev.unb.ca.roompersistencetest.dao.AppDatabase
import mobiledev.unb.ca.roompersistencetest.dao.AppDatabase.Companion.getDatabase
import mobiledev.unb.ca.roompersistencetest.dao.ItemDao
import mobiledev.unb.ca.roompersistencetest.entity.Item

class ItemRepository(application: Application) {
    private val itemDao: ItemDao? = getDatabase(application).itemDao()

    val allItems: LiveData<List<Item>> = itemDao!!.listAllRecords()

    fun insertRecord(name: String?, num: Int) {
        val newItem = Item()
        newItem.name = name
        newItem.num = num
        insert(newItem)
    }

    private fun insert(item: Item) {
        AppDatabase.databaseWriterExecutor.execute { itemDao!!.insert(item) }
    }

    fun deleteRecord(item: Item) {
        AppDatabase.databaseWriterExecutor.execute { itemDao!!.deleteItem(item) }
    }
}
