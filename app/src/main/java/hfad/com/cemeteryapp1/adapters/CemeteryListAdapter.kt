package hfad.com.cemeteryapp1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.Cemetery


class CemeteryListAdapter: ListAdapter<Cemetery, CemeteryListAdapter.ViewHolder>(CemeteryDiffUtilCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) //provided by ListAdater class
        holder.name.text = item.cemeteryName
        holder.location.text = item.cemeteryLocation
//        holder.state.text = item.cemeteryState
//        holder.name.text = item.cemeteryCounty
//        holder.name.text = item.township
//        holder.name.text = item.range
//        holder.name.text = item.section
//        holder.name.text = item.spot
//        holder.firstYear.text =
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.cemetery_list_item, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.cemNameList)
        val location: TextView = itemView.findViewById(R.id.cemLocationList)
        //val state: TextView = itemView.findViewById(R.id.cemStateTv)
//        val county: ImageView = itemView.findViewById(R.id.cemCountyTv)
//        val township: ImageView = itemView.findViewById(R.id.cemTownShipTv)
//        val range: ImageView = itemView.findViewById(R.id.cemRangeTv)
//        val section: ImageView = itemView.findViewById(R.id.cemSectionTv)
//        val spot: ImageView = itemView.findViewById(R.id.cemSpotTv)
//        val gps: ImageView = itemView.findViewById(R.id.cemGPSTv)
//        val firstYear: ImageView = itemView.findViewById(R.id.cemFirstYearTv)
    }
}

class CemeteryDiffUtilCallback: DiffUtil.ItemCallback<Cemetery>(){
    override fun areItemsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean {
        return oldItem == newItem
    }

}