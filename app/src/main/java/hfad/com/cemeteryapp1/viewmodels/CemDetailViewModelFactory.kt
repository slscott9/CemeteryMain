package hfad.com.cemeteryapp1.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hfad.com.cemeteryapp1.database.CemeteryDao

class CemDetailViewModelFactory(
    private val dataSource: CemeteryDao,
    private val id: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CemDetailViewModel::class.java)) {
            return CemDetailViewModel(dataSource, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}