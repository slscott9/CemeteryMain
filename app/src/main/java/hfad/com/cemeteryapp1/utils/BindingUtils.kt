package hfad.com.cemeteryapp1.utils

import android.widget.TextView
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.database.Grave
import org.w3c.dom.Text



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
