package hfad.com.cemeteryapp1.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.adapters.GraveListAdapter
import hfad.com.cemeteryapp1.adapters.GraveListListener
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
    private lateinit var cemeteryViewModel: CemeteryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cemetery_detail,
            container,
            false
        ) //get a reference to our binded layout

        val application = requireNotNull(this.activity).application

        args = CemeteryDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = CemeteryDatabase.getInstance(application).cemeteryDao

        val viewModelFactory = CemeteryViewModelFactory(dataSource, application, args.id)
        cemeteryViewModel =
            ViewModelProvider(this, viewModelFactory).get(CemeteryViewModel::class.java)
        cemeteryViewModel.initializeCemetery(args.id)

        binding.cemeteryViewModel =
            cemeteryViewModel //set the binding variable in xml to our view model class
        binding.lifecycleOwner = this

        binding.addFAB.setOnClickListener {
            view?.findNavController()?.navigate(
                CemeteryDetailFragmentDirections.actionCemeteryDetailFragmentToCreateGraveFragment(
                    args.id
                )
            )
        }

        val adapter = GraveListAdapter(GraveListListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Are you sure you want to delete this cemetery?")
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    cemeteryViewModel.deleteGrave(it)
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Delete grave")
            alert.show()
        })

        cemeteryViewModel.cemeteryWithGraves.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.graveRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_delete_cemetery, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("Are you sure you want to delete this cemetery and all graves?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener{
                    dialogInterface, i -> cemeteryViewModel.deleteCemetery(args.id)
                this.findNavController().navigate(R.id.action_cemeteryDetailFragment_to_cemeteryListFragment2)
            })
            .setNegativeButton("No", DialogInterface.OnClickListener{
                    dialogInterface, i -> dialogInterface.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Delete Cemetery")
        alert.show()

        return false
    }
}