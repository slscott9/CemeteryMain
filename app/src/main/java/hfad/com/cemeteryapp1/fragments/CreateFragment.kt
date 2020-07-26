package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.databinding.FragmentCreateBinding
import hfad.com.cemeteryapp1.viewmodels.CreateViewModel
import hfad.com.cemeteryapp1.viewmodels.CreateViewModelFactory

class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var createViewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CemeteryDatabase.getInstance(application).cemeteryDao //MIGHT BE A PROBLEM   DIFFERENT FROM EXAMPLES

        val viewModelFactory = CreateViewModelFactory(dataSource)

        createViewModel = ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java)

        //add binding




        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_create, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {




        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController())|| super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()

        val name = binding.nameEditText.text.toString()
        val state = binding.stateEditText.text.toString()
        val county = binding.countyEditText.text.toString()
        val townShip = binding.townshipEditText.text.toString()
        val range = binding.rangeEditText.text.toString()
        val section = binding.sectionEditText.text.toString()
        val spot = binding.spotEditText.text.toString()
        val gps = binding.gpsEditText.text.toString()
        val firstYear = binding.firstYearEditText.text.toString()

        val cemetery = Cemetery(cemeteryName = name, cemeteryState = state, cemeteryCounty = county,
            township = townShip, range = range, section = section, spot = spot,
            gps = gps, firstYear = firstYear)

        createViewModel.onUpdate(cemetery)
        Log.i("CreateFragment", "onoptionsitemselected")
    }

}