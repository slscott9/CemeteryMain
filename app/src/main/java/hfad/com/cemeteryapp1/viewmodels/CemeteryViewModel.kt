package hfad.com.cemeteryapp1.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.CemeteryDao
import hfad.com.cemeteryapp1.database.Grave
import kotlinx.coroutines.*


class CemeteryViewModel(val database: CemeteryDao, application: Application, val id: Int?) : AndroidViewModel(application) {

    private var viewModelJob = Job() //1.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)     //3.

    private val _cemetery = MutableLiveData<Cemetery>()      //4.
    val cemetery: LiveData<Cemetery>
        get() = _cemetery

    val cemeteries = database.getAllCemeteries()
    val cemeteryWithGraves = database.getCemeteryGraves(id)


    private val _cemeteryItemNumber =
        MutableLiveData<Int>()
    val cemeteryItemNumber
        get() = _cemeteryItemNumber //expose l

    fun onCemeteryClicked(id: Int) {
        _cemeteryItemNumber.value =
            id //this sets the value of our live data to the id passed from fragment
    }

    fun onCemeteryDetailNavigated() {
        _cemeteryItemNumber.value = null
    }


    //we use this method from CemeteryDetailFragment to get the correct cemetery that was chosen from recycler view.
    //We use the cemetery variable to display the cemetery properties with data binding from cemeteryDetail xml
    fun initializeCemetery(id: Int) {
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

    fun deleteGrave(id: Int){
        uiScope.launch {
            deleteGra(id)
        }
    }

    private suspend fun deleteGra(id: Int){
        withContext(Dispatchers.IO){
            database.deleteGrave(id)
        }
    }

    fun deleteCemetery(id: Int){
        uiScope.launch {
            deleteCem(id)
        }
    }

    private suspend fun deleteCem(id: Int){
        withContext(Dispatchers.IO){
            database.deleteAllGraveWithId(id)
            database.deleteCemetery(id)
        }
    }

    fun onUpdate(cemetery: Cemetery) {
        uiScope.launch {
            update(cemetery)
        }
    }

    private suspend fun update(cemetery: Cemetery) {
        withContext(Dispatchers.IO) {
            database.insertCemetery(cemetery)
            Log.i("CreateViewModel", "update database")

        }
    }

    fun insert(grave: Grave) {
        uiScope.launch {
            insertGrave(grave)
        }
    }

    private suspend fun insertGrave(grave: Grave) {
        withContext(Dispatchers.IO) {
            database.insertGrave(grave)
        }
    }

    fun insert(cemetery: Cemetery) {
        uiScope.launch {
            insertCemetery(cemetery)
        }
    }

    private suspend fun insertCemetery(cemetery: Cemetery) {
        withContext(Dispatchers.IO) {
            database.insertCemetery(cemetery)
        }
    }

    //2. When the view model is destroyed we tell the job to cancel all coroutines, so we dont end up with coroutines that have no wherer to return
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}