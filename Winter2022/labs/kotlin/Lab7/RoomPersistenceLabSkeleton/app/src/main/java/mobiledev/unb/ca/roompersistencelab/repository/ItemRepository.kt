package mobiledev.unb.ca.roompersistencelab.repository

import mobiledev.unb.ca.roompersistencelab.db.AppDatabase.Companion.getDatabase
import android.app.Application
import mobiledev.unb.ca.roompersistencelab.dao.ItemDao

class ItemRepository(application: Application) {
    private val itemDao: ItemDao? = getDatabase(application).itemDao()

    //  See the example project file at
    //  https://github.com/hpowell20/cs2063-winter-2022-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //  to see examples of how to work with the Executor Service

    // TODO Add query specific methods
    //  HINTS
    //   The insert operation can make use of the Runnable interface
    //   As the search operation needs to return results have a look at using Callable interface with Future
    //  Some more details on runnables and callables can be found <a href="https://www.baeldung.com/java-runnable-callable">here</a>
}