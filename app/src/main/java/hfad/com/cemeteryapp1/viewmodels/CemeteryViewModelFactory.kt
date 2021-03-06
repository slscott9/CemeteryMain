package hfad.com.cemeteryapp1.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hfad.com.cemeteryapp1.database.CemeteryDao

class CemeteryViewModelFactory(
    private val dataSource: CemeteryDao,
    private val application: Application,
    private val cemeteryId: Int? = 0
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CemeteryViewModel::class.java)) {
            return CemeteryViewModel(dataSource, application, cemeteryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}