package hfad.com.cemeteryapp1.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.CemeteryDao
import hfad.com.cemeteryapp1.database.Grave
import kotlinx.coroutines.*

/*
        We dont want things slowing down our ui such as pullind data from a database so we use corotines to accomplish this

        The pattern is

            fun someWorkNeedsToBeDone{
                uiScope.launch{             we launch a coroutine that runs on the main or ui thread because the result effects the ui
                    suspendFunction()       inside the scope we call a suspend function to do the long running work, so we dont block the ui thread while waiting for the result
                }
             }

             suspend fun suspendFunction(){         we then define the suspend function, the long  running work has nothing to do with the ui, so we switch the
                    withContext(Dispatcher.IO) {    io context so that we can run in a thread pool that is optimized and set aside  for these kinds of operations
                            longRunningWork()       then we call the database function to do the work
                     }
            }

            1. To manage all of our coroutines, we need a job. This viewModelJob allows us to cancel all coroutines started by this view model when the viewmodel
                is no longer used or destroyed, so we dont end up with coroutines that have no where to return to

            2. When the view model is destroyed we tell the job to cancel all coroutines, so we dont end up with coroutines that have no wherer to return

            3. we need a scope for our coroutines to run in. The scope determines what thread the coroutine will run on. It also needs to know about the job
                    - to get the job we ask for an instance of CoroutineScope, and pass in a Dispatcher and a job
                        our Dispatcher here is Dispatcher.Main - this means coroutines launched in the uiScope will run on the main thread
                            This is sensible for many coroutines started by a view model, as they will eventually result in an update of the ui after performing some processing

            4. make variable mutable live data so we can change it. Since its live data in the Dao class room takes care of updating changes for us

            5. we need the cemetery set as soon as possible so we can work with it

            6. we are using a coroutine to get cemetery from the database, so that we are not blocking the ui while waiting for the result

                we need to specify the scope, and in that scope we launch a coroutine. Launching the coroutine creates the coroutine without blocking the current thread in the context
                defined by the current scope. Unless you specify otherwise the coroutine is scheduled to execute immediately.
                The work we want done is get the value for cemetery from the data base, by calling getCemeteryFromDatabase()

            7. we mark it as suspend, because we want to call it from inside the coroutine, and not block the main thread and we want to return a Cemetery or null

            17. from CemeteryListFragment we pass the id of the recycler view row that was clicked into our view model
 */
//When the view model is destroyed, onCleared() is called. We can override this method to cancel all coroutines started by this view model

class CemeteryViewModel(val database: CemeteryDao, application: Application, val id: Int?) : AndroidViewModel(application){

    private var viewModelJob = Job() //1.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)     //3.

    private val _cemetery = MutableLiveData<Cemetery>()      //4.
    val cemetery: LiveData<Cemetery>
        get() = _cemetery

    val cemeteries = database.getAllCemeteries()
    val cemeteryWithGraves = database.getCemeteryGraves(id)


    private val _cemeteryItemNumber = MutableLiveData<Int>() //17. from CemeteryListFragment we pass the id of the recycler view row that was clicked into our view model
    val cemeteryItemNumber
    get() = _cemeteryItemNumber //expose l

    fun onCemeteryClicked(id: Int){
        _cemeteryItemNumber.value = id //this sets the value of our live data to the id passed from fragment
    }

    fun onCemeteryDetailNavigated(){
        _cemeteryItemNumber.value = null
    }


    //we use this method from CemeteryDetailFragment to get the correct cemetery that was chosen from recycler view.
    //We use the cemetery variable to display the cemetery properties with data binding from cemeteryDetail xml
    fun initializeCemetery(id: Int){
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


    fun onUpdate(cemetery: Cemetery){
        uiScope.launch {
            update(cemetery)
        }
    }
    private suspend fun update(cemetery: Cemetery){
        withContext(Dispatchers.IO){
            database.insertCemetery(cemetery)
            Log.i("CreateViewModel", "update database")

        }
    }

    fun insert(grave: Grave){
        uiScope.launch {
            insertGrave(grave)
        }
    }

    private suspend  fun insertGrave(grave: Grave){
        withContext(Dispatchers.IO){
            database.insertGrave(grave)
        }
    }

    //2. When the view model is destroyed we tell the job to cancel all coroutines, so we dont end up with coroutines that have no wherer to return
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }





//STILL WORKS WITHOUT ANY OF THIS
//    private suspend fun insert(cemetery: Cemetery){
//        withContext(Dispatchers.IO){
//            database.insertCemetery(cemetery)
//        }
//    }
//
//    fun onUpdateCemetery(){
//        uiScope.launch {
//            val newCem = Cemetery()
//            update(newCem)
//            cemetery.value = getCemeteryFromDatabase()
//        }
//    }
//
//    private suspend fun update(cemetery: Cemetery){
//        withContext(Dispatchers.IO){
//            database.updateCemetery(cemetery)
//        }
//    }

    //6. we are using a coroutine to get cemetery from the database, so that we are not blocking the ui while waiting for the result
//    private fun initializeCemetery(){
//
//        uiScope.launch {
//            cemetery.value = getCemeteryFromDatabase() //we want to make sure that this method does not block, and we want to return a cemetery or null
//            Log.i(
//                "CemeteryViewModel",
//                "Called initializeCemetery in init cemetery.name is"
//            )
//        }
//    }

//    //7. we mark it as suspend, because we want to call it from inside the coroutine, and not block the main thread and we want to return a Cemetery or null
//    private suspend fun getCemeteryFromDatabase(): Cemetery?{
//        //so how do we get the cemetery to return? We create another coroutine with the IO context, using the IO dispatcher
//
//        return withContext(Dispatchers.IO){
//            val cemetery = database.getCemetery()
//
//            cemetery
//        }
//    }
//
//    fun onCreateCemetery(){
//        uiScope.launch {
//            val newCem = Cemetery()
//            insert(newCem)
//            cemetery.value = getCemeteryFromDatabase()
//        }
//    }
}