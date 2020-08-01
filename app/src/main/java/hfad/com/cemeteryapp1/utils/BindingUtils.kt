package hfad.com.cemeteryapp1.utils

import android.widget.TextView
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.Grave
import org.w3c.dom.Text

/*
    We used binding to set our views in the adapter's ViewHolder, so far the code just uses the binding object to avoid calling findViewById
    We can update this to use data binding to set our views for us. To do this we need to make binding adapters.
    Binding adapters are just like recycler view adapters. Its an adapter that adapts our application data into something data binding can display
    on the screen

    To make a binding adapter you make a method that takes a view and some data. The binding adapter is responsible for updating the view to represent the data
    we will need two one for the name and one for the location. We can write the adapter as an extention function

    IMPORTANT - make sure to use the Cemetery @Entity class as the list_item xml variable

    1. we have to bind the data to the views that is what makes it a binding adapter, the method adapts our data to something the binding can use
 */

@BindingAdapter("setCemeteryName")
fun TextView.setCemeteryName(item: Cemetery?){
    item?.cemeteryName?.let {
        text = item.cemeteryName
    }
}

@BindingAdapter("setCemeteryLocation")
fun TextView.setCemeteryLocation(item: Cemetery?){
    item?.cemeteryLocation?.let {
        text = item.cemeteryLocation
    }
}

//@BindingAdapter("setGraveName")
//fun TextView.setGraveName(item: CemeteryWithGraves?){
//    item?.let {
//        text = item.graves
//    }
//}
