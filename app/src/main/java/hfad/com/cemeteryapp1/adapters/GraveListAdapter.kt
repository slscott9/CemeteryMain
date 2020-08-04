package hfad.com.cemeteryapp1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.Grave
import hfad.com.cemeteryapp1.databinding.GraveListItemBinding
import kotlinx.android.synthetic.main.grave_list_item.view.*


class GraveListAdapter(val clickListener: GraveListListener): ListAdapter<Grave, GraveListAdapter.ViewHolder>(GraveDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GraveListItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    /*
        set the current Grave object so binding can its properties
        set the click listener for image view delete button
     */
    class ViewHolder(val binding: GraveListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Grave, listener: GraveListListener){
            binding.grave = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }
}

class GraveDiffUtilCallback: DiffUtil.ItemCallback<Grave>(){
    override fun areItemsTheSame(oldItem: Grave, newItem: Grave): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Grave, newItem: Grave): Boolean {
        return oldItem == newItem
    }
}
class GraveListListener(val clickListener: (id: Int) -> Unit){
    fun onClick(grave: Grave){
        clickListener(grave.id)
    }
}