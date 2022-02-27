package mobiledev.unb.ca.roompersistencetest.repository

import mobiledev.unb.ca.roompersistencetest.dao.ItemDao
import androidx.lifecycle.LiveData
import mobiledev.unb.ca.roompersistencetest.entity.Item

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: LiveData<List<Item>> = itemDao.listAllRecords()

    fun insertRecord(name: String?, num: Int) {
        val newItem = Item()
        newItem.name = name
        newItem.num = num
        insert(newItem)
    }

    private fun insert(item: Item) {
        itemDao.insert(item)
    }

    fun deleteRecord(item: Item) {
        itemDao.deleteItem(item)
    }
}
