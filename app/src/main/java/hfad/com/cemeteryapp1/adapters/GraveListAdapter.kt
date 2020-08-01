package hfad.com.cemeteryapp1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hfad.com.cemeteryapp1.R
import hfad.com.cemeteryapp1.database.Grave
import kotlinx.android.synthetic.main.grave_list_item.view.*

//val clickListener: GraveListListener

class GraveListAdapter(): ListAdapter<Grave, GraveListAdapter.ViewHolder>(GraveDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.grave_list_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.firstName.text = item.first
        holder.lastName.text = item.last
        holder.birthYear.text = item.born
        holder.deathYear.text = item.death
        holder.marriageYear.text = item.married
        holder.comment.text = item.comment
        holder.graveNumber.text = item.graveNumber


    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
       val firstName = view.graveNameTextview
       val lastName = view.lastTextView
       val birthYear = view.birthTextView
       val deathYear = view.deathTextview
       val marriageYear = view.marriageYearTextView
       val comment = view.commentTextView
       val graveNumber = view.graveNumberTextView

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