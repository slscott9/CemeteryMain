package hfad.com.cemeteryapp1.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.adapters.CemeteryListAdapter
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.databinding.FragmentCemeteryListBinding
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModelFactory

class CemeteryListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCemeteryListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cemetery_list, container, false)

//        binding.button.setOnClickListener {
//            view?.findNavController()?.navigate(R.id.action_cemeteryListFragment_to_cemeteryDetailFragment)
//
//        }

        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application //1. requreNotNull is kotlin function that throws an illegal argument exception if the value is null

        val dataSource = CemeteryDatabase.getInstance(application).cemeteryDao //2. get reference to database via the reference to the dao
        Log.i("MainActivity", "Database created")


        //3. Now we can create an instance of the view model factory, we must pass it the data source and the application
        val viewModelFactory = CemeteryViewModelFactory(dataSource, application)

        //4. now that we have a factory we can ask ViewModelProvider for a view model
        val cemeteryViewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryViewModel::class.java)


        //7. we can set the variable in our layout, which we access through the binding object to the view model (MAY HAVE TO CLEAN REBUILD if it throws error because binding might not
                                                                                                                    //know about view mmodel yet
        binding.cemeteryViewModel = cemeteryViewModel


        //5. now that we have a view model we also need to finish setting up our data binding. We also need to connect our view model to our user interface
        binding.lifecycleOwner = this //specify the current activity as the life cycle owner of the binding. This is necessary so that the binding can observe live data updates

        val adapter = CemeteryListAdapter()

        cemeteryViewModel.cemeteries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.cemeterListRecyclerView.adapter = adapter


        //6. our layout needs to know about the view model. Then we can reference functions and data in the view model from the layout, to display live data. do this in xml file (cem_list)

                /*
                    in xml file (cem_list_frag)
                    1. create a data tag with a variable that references the view model
                    <data>
                         <variable
                                name="cemeteryViewModel"
                            type="hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel" />
                    </data>
                 */
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController())|| super.onOptionsItemSelected(item)
    }

}