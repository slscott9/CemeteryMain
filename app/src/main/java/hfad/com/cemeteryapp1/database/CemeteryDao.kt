package hfad.com.cemeteryapp1.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CemeteryDao {

    @Insert
    fun insertCemetery(cemetery:Cemetery)


    @Update
    fun updateCemetery(cemetery: Cemetery)

//    @Delete
//    fun deleteCemetery()

    @Query("SELECT * FROM cemeteries ORDER BY name DESC")
    fun getAllCemeteries() : LiveData<List<Cemetery>>

//    @Query("SELECT name FROM cemeteries")
//    fun getAllCemeteryNames() : LiveData<List<Cemetery>>

    @Query("SELECT * FROM cemeteries ORDER BY id DESC LIMIT 1")
    fun getCemetery(): Cemetery?

    @Query("SELECT * FROM cemeteries WHERE id = :id")
    fun getCemeteryWithId(id: Int): Cemetery?


}