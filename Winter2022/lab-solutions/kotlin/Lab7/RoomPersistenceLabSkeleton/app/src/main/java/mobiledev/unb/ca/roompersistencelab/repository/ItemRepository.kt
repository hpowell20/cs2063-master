package mobiledev.unb.ca.roompersistencelab.repository

import android.app.Application
import mobiledev.unb.ca.roompersistencelab.dao.ItemDao
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase.Companion.getDatabase
import mobiledev.unb.ca.roompersistencelab.entity.Item
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class ItemRepository(application: Application) {
    private val itemDao: ItemDao? = getDatabase(application).itemDao()

    //  See the example project file at
    //  https://github.com/hpowell20/cs2063-winter-2022-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //  to see examples of how to work with the Executor Service

    // TODO Add query specific methods
    //  HINTS
    //   The insert operation can make use of the Runnable interface
    //   As the search operation needs to return results have a look at using Callable interface with Future
    //   Some more details on runnables and callables can be found <a href="https://www.baeldung.com/java-runnable-callable">here</a>
    //   See the example project file at
    //   https://github.com/hpowell20/cs2063-winter-2022-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //   to see examples of how to work with the Executor Service
    fun insertRecord(name: String?, num: Int) {
        val newItem = Item()
        newItem.name = name
        newItem.num = num
        insertRecord(newItem)
    }

    private fun insertRecord(item: Item) {
        AppDatabase.databaseWriterExecutor.execute { itemDao!!.insert(item) }
    }

    fun findItemsWithName(name: String?): List<Item> {
        val dataReadFuture: Future<List<Item>> = AppDatabase.databaseWriterExecutor.submit(
            Callable {
                itemDao!!.findItemsWithName(name)
            })
        return try {
            while (!dataReadFuture.isDone) {
                // Simulating another task
                TimeUnit.SECONDS.sleep(1)
            }
            dataReadFuture.get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
            emptyList()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            emptyList()
        }
    }
}