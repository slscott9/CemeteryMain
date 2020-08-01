package hfad.com.cemeteryapp1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.databinding.CemeteryListItemBinding


class CemeteryListAdapter(val clickListener: CemeteryListener): ListAdapter<Cemetery, CemeteryListAdapter.ViewHolder>(CemeteryDiffUtilCallback()){ //1.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //2.
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CemeteryListItemBinding.inflate(layoutInflater, parent, false) //3. use binding to inflate the list_item layout

        return ViewHolder(binding) //4. alt enter on view parameter change to item view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //6.
        val item = getItem(position) //provided by ListAdater class, we now have the object at the position in our list. We can use it to access its properties and set our views with it.
        holder.bind(item, clickListener) //9.
    }

    class ViewHolder (val binding: CemeteryListItemBinding) : RecyclerView.ViewHolder(binding.root){ //7.

        //binding.root is the <layout> tag from list_item xml
        fun bind(
            item: Cemetery,
            clickListener: CemeteryListener
        ){   //8. gives our cemetery <data> binding tag data to bind
            binding.cemetery = item
            binding.clickListener = clickListener //14.
            binding.executePendingBindings()
        }
    }
}
//diff util calulates the minimum changes needed it is more efficient. Only updates the items that are changed.
//create diff util class that handles changes for us
class CemeteryDiffUtilCallback: DiffUtil.ItemCallback<Cemetery>(){
    override fun areItemsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Cemetery, newItem: Cemetery): Boolean {
        return oldItem == newItem
    }
}
//10.
class CemeteryListener(val clickListener: (id: Int) -> Unit){
    fun onClick(cemetery: Cemetery){
        clickListener(cemetery.id)
    }
}