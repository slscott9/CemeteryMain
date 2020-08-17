package hfad.com.cemeteryapp1.fragments

import android.Manifest
import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.R.id.action_createGraveFragment_to_cemeteryDetailFragment
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.database.Grave
import hfad.com.cemeteryapp1.fragments.CemeteryDetailFragmentArgs.fromBundle
import hfad.com.cemeteryapp1.generated.callback.OnClickListener
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModelFactory
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create_grave.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class CreateGraveFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: CemeteryViewModel
    private lateinit var args: CreateGraveFragmentArgs
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Initialize the Fused location variable

        //args = CreateGraveFragment.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application

        val database = CemeteryDatabase.getInstance(application).cemeteryDao

        args = CreateGraveFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = CemeteryViewModelFactory(database, application, args.cemId)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryViewModel::class.java)

        dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                updateDateInView()
            }
        return inflater.inflate(R.layout.fragment_create_grave, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bornEt.setOnClickListener(this)
        deathYearEt.setOnClickListener(this)
        marriageYearEt.setOnClickListener(this)
    }

    override fun onStop() {
        super.onStop()

        val first = firstNameEt.text.toString()
        val last = lastNameet.text.toString()
        val born = bornEt.text.toString()
        val died = deathYearEt.text.toString()
        val married = marriageYearEt.text.toString()
        val comment = commentEt.text.toString()
        val graveNum = graveNumEt.text.toString()

        val grave = Grave(first = first, last = last, born = born, death = died, married = married, comment = comment, graveNumber = graveNum, cemId = args.cemId)
        viewModel.insert(grave)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.create_grave_menu, menu)
    }

    override fun onOptionsItemSelected (item: MenuItem) : Boolean {

        when(item.itemId){
            R.id.saveGrave -> {
                this.findNavController().navigate(CreateGraveFragmentDirections.actionCreateGraveFragmentToCemeteryDetailFragment(args.cemId))
                return true
            }
            else -> return false
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault()) // A date format
        deathYearEt.setText(sdf.format(cal.time).toString()) // A selected date using format which we have used is set to the UI.
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.deathYearEt -> {
                DatePickerDialog(requireActivity(), dateSetListener, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR)).show()
            }

            R.id.bornEt -> {
                DatePickerDialog(requireActivity(), dateSetListener, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR)).show()
            }

            R.id.marriageYearEt -> {
                DatePickerDialog(requireActivity(), dateSetListener, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR)).show()
            }
        }
    }

//    fun CheckPermission():Boolean{
//        //this function will return a boolean
//        //true: if we have permission
//        //false if not
//        if(
//            ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
//            ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//        ){
//            return true
//        }
//
//        return false
//
//    }
//
//    fun RequestPermission(){
//        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
//        ActivityCompat.requestPermissions(
//            requireActivity(),
//            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION)
//        )
//    }


}