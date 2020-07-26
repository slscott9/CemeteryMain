package hfad.com.cemeteryapp1.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.CemeteryDao
import kotlinx.coroutines.*

class CreateViewModel(val dataSource: CemeteryDao, private val id: Int = 0): ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onUpdate(cemetery: Cemetery){
        uiScope.launch {
            update(cemetery)
        }
    }
    private suspend fun update(cemetery: Cemetery){
        withContext(Dispatchers.IO){
            dataSource.insertCemetery(cemetery)
            Log.i("CreateViewModel", "update database")

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}