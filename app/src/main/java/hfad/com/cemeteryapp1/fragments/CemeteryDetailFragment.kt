package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.adapters.GraveListAdapter
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.databinding.FragmentCemeteryDetailBinding
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModelFactory
import kotlin.properties.Delegates

//get the arguments from the bundle sent from CemeteryListFragment - the id that was clicked from rv was passed
//get a reference to our database so we can pass it to the factory (constucts our cemDetailviewModel)
//get our factory instance passing it the data source and id from cemeterylistfragment

class CemeteryDetailFragment : Fragment() {

    private lateinit var binding: FragmentCemeteryDetailBinding
    private lateinit var args: CemeteryDetailFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cemetery_detail, container, false) //get a reference to our binded layout

        val application = requireNotNull(this.activity).application

        args = CemeteryDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = CemeteryDatabase.getInstance(application).cemeteryDao

        val viewModelFactory = CemeteryViewModelFactory(dataSource, application, args.id)
        val cemeteryViewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryViewModel::class.java)
        cemeteryViewModel.initializeCemetery(args.id)

        binding.cemeteryViewModel = cemeteryViewModel //set the binding variable in xml to our view model class
        binding.lifecycleOwner = this

        binding.addFAB.setOnClickListener{
            view?.findNavController()?.navigate(CemeteryDetailFragmentDirections.actionCemeteryDetailFragmentToCreateGraveFragment(args.id))///
        }

        val adapter = GraveListAdapter()

        cemeteryViewModel.cemeteryWithGraves.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.graveRecyclerView.adapter = adapter
        return binding.root
    }












}