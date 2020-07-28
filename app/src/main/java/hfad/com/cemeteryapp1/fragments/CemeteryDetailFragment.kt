package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.CemeteryDao
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.databinding.FragmentCemeteryDetailBinding
import hfad.com.cemeteryapp1.viewmodels.CemDetailViewModel
import hfad.com.cemeteryapp1.viewmodels.CemDetailViewModelFactory


class CemeteryDetailFragment : Fragment() {

    private lateinit var binding: FragmentCemeteryDetailBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = this.findNavController()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cemetery_detail, container, false) //get a reference to our binded layout

        val application = requireNotNull(this.activity).application
        val arguments = CemeteryDetailFragmentArgs.fromBundle(requireArguments()) //get the arguments from the bundle sent from CemeteryListFragment - the id that was clicked from rv was passed
        val dataSource = CemeteryDatabase.getInstance(application).cemeteryDao                //get a reference to our database so we can pass it to the factory (constucts our cemDetailviewModel)
        val viewModelFactory = CemDetailViewModelFactory(dataSource, arguments.id)                          //get our factory instance passing it the data source and id from cemeterylistfragment

        val cemDetailViewModel = ViewModelProvider(this, viewModelFactory).get(CemDetailViewModel::class.java) //create view model passing the factory and context

        binding.cemDetailViewModel = cemDetailViewModel //set the biinding variable in xml to our view model class
        binding.lifecycleOwner = this

        return binding.root
    }



}