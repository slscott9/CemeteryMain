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


@BindingAdapter("setGraveFirstName")
fun TextView.setGraveFirst(item: Grave?){
    item?.first?.let {
        text = item.first
    }
}

@BindingAdapter("setGraveLast")
fun TextView.setGraveLast(item: Grave?){
    item?.last?.let {
        text = item.last
    }
}

@BindingAdapter("setGraveBirth")
fun TextView.setGraveBirth(item: Grave?){
    item?.born?.let {
        text = item.born
    }
}

@BindingAdapter("setGraveDeath")
fun TextView.setGraveDeath(item: Grave?){
    item?.death?.let {
        text = item.death
    }
}

@BindingAdapter("setGraveMarried")
fun TextView.setGraveMarried(item: Grave?){
    item?.married?.let {
        text = item.married
    }
}

@BindingAdapter("setGraveComment")
fun TextView.setGraveComment(item: Grave?){
    item?.comment?.let {
        text = item.comment
    }
}

@BindingAdapter("setGraveNum")
fun TextView.setGraveNum(item: Grave?){
    item?.graveNumber?.let {
        text = item.graveNumber
    }
}






