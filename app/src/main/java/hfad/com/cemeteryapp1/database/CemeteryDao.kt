package hfad.com.cemeteryapp1.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CemeteryDao {

    @Insert
    fun insertCemetery(cemetery:Cemetery)


    @Update
    fun updateCemetery(cemetery: Cemetery)

    @Insert
    fun insertGrave(grave: Grave)


    @Query("SELECT * FROM cemeteries ORDER BY name DESC")
    fun getAllCemeteries() : LiveData<List<Cemetery>>



    @Query("SELECT * FROM cemeteries ORDER BY cemetery_id DESC LIMIT 1")
    fun getCemetery(): Cemetery

    @Query("SELECT * FROM cemeteries WHERE cemetery_id = :id")
    fun getCemeteryWithId(id: Int): Cemetery

    @Query("SELECT * FROM graves WHERE cemetery_id = :id")
    fun getCemeteryGraves(id: Int?) : LiveData<List<Grave>>


}