package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.CemeteryDatabase
import hfad.com.cemeteryapp1.database.Grave
import hfad.com.cemeteryapp1.fragments.CemeteryDetailFragmentArgs.fromBundle
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModel
import hfad.com.cemeteryapp1.viewmodels.CemeteryViewModelFactory
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create_grave.*
import kotlin.properties.Delegates

class CreateGraveFragment : Fragment() {

    private lateinit var viewModel: CemeteryViewModel
    val args: CreateGraveFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        //args = CreateGraveFragment.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val database = CemeteryDatabase.getInstance(application).cemeteryDao
        val viewModelFactory = CemeteryViewModelFactory(database, application, args.id.toInt())

        viewModel = ViewModelProvider(this, viewModelFactory).get(CemeteryViewModel::class.java)

        return inflater.inflate(R.layout.fragment_create_grave, container, false)
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

        val grave = Grave(first = first, last = last, born = born, death = died, married = married, comment = comment, graveNumber = graveNum, cemId = args.id)
        Log.i("CreateGraveFragment", "The id from passed to this fragment is ${args.id}")
        viewModel.insert(grave)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.create_grave_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())|| super.onOptionsItemSelected(item)


    }
}