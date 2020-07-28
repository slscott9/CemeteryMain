package hfad.com.cemeteryapp1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.CemeteryDao
import kotlinx.coroutines.*

class CemDetailViewModel(val database: CemeteryDao, cemId: Int): ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _cemetery = MutableLiveData<Cemetery>()
    val cemetery: LiveData<Cemetery>
    get() = _cemetery

    init {
        initializeCemetery(cemId)
    }

    private fun initializeCemetery(id: Int){
        uiScope.launch {
            _cemetery.value = getCemeteryWithId(id)
        }
    }

    private suspend fun getCemeteryWithId(id: Int): Cemetery? {
        return withContext(Dispatchers.IO) {
            val cemetery = database.getCemeteryWithId(id)

            cemetery
        }
    }
}