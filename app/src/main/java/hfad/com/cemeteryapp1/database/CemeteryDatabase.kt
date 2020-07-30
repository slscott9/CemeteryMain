package hfad.com.cemeteryapp1.database

import android.content.Context
import android.util.Log
import androidx.navigation.Navigator
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


//MAKE SURE TO SPECIFY EACH ENTITY!!!!

@Database(entities = [Cemetery::class, Grave::class], version = 28, exportSchema = false)
abstract class CemeteryDatabase: RoomDatabase() {

    abstract val cemeteryDao: CemeteryDao

    companion object {
        @Volatile
        private var INSTANCE: CemeteryDatabase? = null

        fun getInstance(context: Context): CemeteryDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CemeteryDatabase::class.java,
                        "cemetery_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    Log.i("Database", "Created")
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}