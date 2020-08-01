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

@Database(entities = [Cemetery::class, Grave::class], version = 32, exportSchema = false)
abstract class CemeteryDatabase: RoomDatabase() {

    abstract val cemeteryDao: CemeteryDao

    companion object {
        @Volatile
        private var INSTANCE: CemeteryDatabase? = null

        fun getInstance(context: Context): CemeteryDatabase {
            synchronized(this) {

                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CemeteryDatabase::class.java,
                        "cemetery_database"
                    )
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