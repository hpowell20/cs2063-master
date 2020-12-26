package mobiledev.unb.ca.roompersistencetest.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mobiledev.unb.ca.roompersistencetest.dao.ItemDao;
import mobiledev.unb.ca.roompersistencetest.entity.Item;

/**
 * Database layer in top of the SQLite database
 */
@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {
    private static volatile ItemRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public abstract ItemDao itemDao();
    // Define an ExecutorService with a fixed thread pool which is used to run database operations asynchronously on a background thread
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton access to the database
    public static ItemRoomDatabase getDatabase(final Context context) {
        if (null == INSTANCE) {
            synchronized (ItemRoomDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
