package mobiledev.unb.ca.roompersistencelab;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Database layer in top of the SQLite database
 */
@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract ItemDao itemDao();

    // Singleton access to the database
    public static AppDatabase getDatabase(final Context context) {
        if (null == INSTANCE) {
            synchronized (AppDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
