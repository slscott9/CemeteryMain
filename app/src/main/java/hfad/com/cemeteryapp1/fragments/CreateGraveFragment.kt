package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.Grave
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create_grave.*

class CreateGraveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_grave, container, false)
    }

    override fun onStop() {
        super.onStop()

//        val first = firstNameEt.text.toString()
//        val last = lastNameet.text.toString()
//        val born = bornEt.text.toString()
//        val died = deathYearEt.text.toString()
//        val married = marriageYearEt.text.toString()
//        val comment = commentEt.text.toString()
//        val graveNum = graveNumEt.text.toString()
//        val cemeteryId = cemeteryIdEt.text.toString()
//
//        val grave = Grave(first = first, last = last, born = born, death = died, married = married, comment = comment, graveNumber = graveNum, graveId = cemeteryId)
    }
}