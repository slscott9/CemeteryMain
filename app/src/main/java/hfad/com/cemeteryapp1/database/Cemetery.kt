package hfad.com.cemeteryapp1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cemeteries")
data class Cemetery(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    val cemeteryName: String? = "",

    @ColumnInfo(name = "location")
    val cemeteryLocation: String = "",

    @ColumnInfo(name = "state")
    val cemeteryState: String? = "",

    @ColumnInfo(name = "county")
    val cemeteryCounty: String? = "",

    @ColumnInfo(name = "township")
    val township: String? ="",

    @ColumnInfo(name = "range")
    val range: String? ="",

    @ColumnInfo(name = "spot")
    val spot: String? ="",

    @ColumnInfo(name = "first_year")
    val firstYear: String? ="",

    @ColumnInfo(name = "section")
        val section: String? ="",

    @ColumnInfo(name = "gps")
    val gps: String? =""
) {
}