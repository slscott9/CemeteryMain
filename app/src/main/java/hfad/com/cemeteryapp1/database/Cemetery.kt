package hfad.com.cemeteryapp1.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "cemeteries")
data class Cemetery(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cemetery_id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val cemeteryName: String,

    @ColumnInfo(name = "location")
    val cemeteryLocation: String,

    @ColumnInfo(name = "state")
    val cemeteryState: String,

    @ColumnInfo(name = "county")
    val cemeteryCounty: String,

    @ColumnInfo(name = "township")
    val township: String?,

    @ColumnInfo(name = "range")
    val range: String?,

    @ColumnInfo(name = "spot")
    val spot: String?,

    @ColumnInfo(name = "first_year")
    val firstYear: String?,

    @ColumnInfo(name = "section")
        val section: String,

    @ColumnInfo(name = "gps")
    val gps: String
)

@Entity(tableName = "graves")
data class Grave(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "cemetery_id")
    val cemId: Int,

    @ColumnInfo(name = "first_name")
    val first: String,

    @ColumnInfo(name = "last_name")
    val last: String? ="",

    @ColumnInfo(name = "born_data")
    val born: String,

    @ColumnInfo(name = "death_data")
    val death: String,

    @ColumnInfo(name = "married")
    val married: String,

    @ColumnInfo(name = "comment")
    val comment: String,

    @ColumnInfo(name = "grave_number")
    val graveNumber: String
)
