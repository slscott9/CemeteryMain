package hfad.com.cemeteryapp1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hfad.com.cemeteryapp1.R


class GraveDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //val cemDetailViewModel = ViewModelProvider(this).get(CemDetailViewModel::class.java)
        return inflater.inflate(R.layout.fragment_grave_detail, container, false)
    }


}