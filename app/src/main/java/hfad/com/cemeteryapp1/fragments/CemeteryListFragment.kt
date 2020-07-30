package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.adapters.CemeteryListAdapter
import hfad.com.cemeteryapp1.adapters.CemeteryListener
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.databinding.FragmentCemeteryListBinding
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModelFactory
/*
    1. requreNotNull is kotlin function that throws an illegal argument exception if the value is null

    2. get reference to database via the reference to the dao

    3. Now we can create an instance of the view model factory, we must pass it the data source and the application

    4. now that we have a factory we can ask ViewModelProvider for a view model

    5. now that we have a view model we also need to finish setting up our data binding. We also need to connect our view model to our user interface

    6. our layout needs to know about the view model. Then we can reference functions and data in the view model from the layout, to display live data. do this in xml file (cem_list)
    6. our layout needs to know about the view model. Then we can reference functions and data in the view model from the layout, to display live data. do this in xml file (cem_list)

                /*
                    in xml file (cem_list_frag)
                    1. create a data tag with a variable that references the view model
                    <data>
                         <variable
                                name="cemeteryViewModel"
                            type="hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel" />
                    </data>
                 */

     7. we can set the variable in our layout, which we access through the binding object to the view model (MAY HAVE TO CLEAN REBUILD if it throws error because binding might not
                                                                                                                    //know about view mmodel yet
     15.  pass listener to the adpater so it can set the listener using data binding in our xml list_item

     16. pass the id of the row in recycler view that was clicked to the view model
 */
class CemeteryListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCemeteryListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cemetery_list, container, false)

        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application //1.
        val dataSource = CemeteryDatabase.getInstance(application).cemeteryDao //2.
        Log.i("MainActivity", "Database created")

        //3.
        val viewModelFactory = CemeteryViewModelFactory(dataSource, application)
        //4.
        val cemeteryViewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryViewModel::class.java)
        //7.
        binding.cemeteryViewModel = cemeteryViewModel
        //5.
        binding.lifecycleOwner = this //specify the current activity as the life cycle owner of the binding. This is necessary so that the binding can observe live data updates

        //15.
        val adapter = CemeteryListAdapter(CemeteryListener {
            id -> cemeteryViewModel.onCemeteryClicked(id) //16.
            Log.i("CemeteryListFragment", "Callback called id is $id")
        })

        cemeteryViewModel.cemeteries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it) //list adpater class method to update listj
            }
        })
        binding.cemeterListRecyclerView.adapter = adapter

        //if it(the row number ) is not null then navigate to detail fragment and pass it(the row number)
        cemeteryViewModel.cemeteryItemNumber.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(CemeteryListFragmentDirections.actionCemeteryListFragmentToCemeteryDetailFragment(it))
                cemeteryViewModel.onCemeteryDetailNavigated() //MUST SET THIS OR THE FRAGMENT WILL NOT GO BACK ON BACK BUTTON PRESS
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())|| super.onOptionsItemSelected(item)
    }

}