package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.databinding.FragmentCreateBinding
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModelFactory
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create_grave.*

class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var createViewModel: CemeteryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = CemeteryDatabase.getInstance(application).cemeteryDao //MIGHT BE A PROBLEM   DIFFERENT FROM EXAMPLES
        val viewModelFactory = CemeteryViewModelFactory(dataSource, application)

        createViewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryViewModel::class.java)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_create, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cemeterySaveItem -> {
                this.findNavController().navigate(CreateFragmentDirections.actionCreateFragmentToCemeteryListFragment())
                return true
            }
            else -> return false
        }
    }

    override fun onStop() {
        super.onStop()

        val name = nameEditText.text.toString()
        val location = locationEditText.text.toString()
        val state = stateEditText.text.toString()
        val county = countyEditText.text.toString()
        val townShip = townshipEditText.text.toString()
        val range = rangeEditText.text.toString()
        val section = sectionEditText.text.toString()
        val spot = spotEditText.text.toString()
        val gps = gpsEditText.text.toString()
        val firstYear = firstYearEditText.text.toString()

        if(name.isEmpty() && location.isEmpty() && state.isEmpty() && county.isEmpty() && townShip.isEmpty() && range.isEmpty() && section.isEmpty() &&
                spot.isEmpty() && gps.isEmpty() && firstYear.isEmpty()){
            return
        }

        val cemetery = Cemetery(cemeteryLocation = location, cemeteryName = name, cemeteryState = state, cemeteryCounty = county,
            township = townShip, range = range, section = section, spot = spot,
            gps = gps, firstYear = firstYear)

            createViewModel.insert(cemetery)

        Log.i("CreateFragment", "onoptionsitemselected")
    }
}