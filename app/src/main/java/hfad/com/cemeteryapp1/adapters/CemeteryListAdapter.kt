package hfad.com.cemeteryapp1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hfad.com.cemeteryapp1.database.Cemetery
import hfad.com.cemeteryapp1.databinding.CemeteryListItemBinding

/*
        1. We use data binding in the list_item xml it has a reference to our database
        2. we need to make the adapter log data to the layout using data binding now

        One pattern to do this is to is to update our ViewHolder to hold binding objects. The big idea about data binding is to create an object that
        connects, maps, and binds two peices of information together at compile time, so that you dont have to look for it at run time.
        The object that surfaces these bindings to you is the binding object (camel case of layout name with Binding at end). Its created by the compiler.

        Recycler view doesnt know anything about binding objects or data binding. This is okay because we can stil expose recyelr view adapter api.
        This is part of the power of recycler view adapter's api. It lets us design our code in a way that makes sense to us as long as we meet the requirements of the adapter
        interface.

        May have to rebuild project to generate the binding object

        3. now that we have the binding object we can use it to inflate the layout for us. Replace val view = layoutInflater() with binding object

        4. alt enter on view parameter change to item view

        5. make binding a property in onCreateViewHolder

        9. we go into list_item xml and use app:setCemeteryName="@{@cemetery}"
            -setCemeteryName is the annotation name we gave our binding adapter in Binding.Util. cemetery is the binding object variable name in the xml
            - it is populated with data from our Bind.Util adapters our views will be automatically updated through the binding adapters
         */

class CemeteryListAdapter: ListAdapter<Cemetery, CemeteryListAdapter.ViewHolder>(CemeteryDiffUtilCallback()){ //1.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //2. inflate our list item layout the recycler view will use for each row
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CemeteryListItemBinding.inflate(layoutInflater, parent, false) //3. use binding to inflate the list_item layout

        return ViewHolder(binding) //4. alt enter on view parameter change to item view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //6. set our ViewGroups views
        val item = getItem(position) //provided by ListAdater class, we now have the object at the position in our list. We can use it to access its properties and set our views with it.
//        holder.binding.cemNameList.text = item.cemeteryName
//        holder.binding.cemLocationList.text = item.cemeteryLocation

        holder.bind(item) //9. call the method that binds data to our binding object in list_item xml.
    }

    class ViewHolder (val binding: CemeteryListItemBinding) : RecyclerView.ViewHolder(binding.root){ //7. make binding a val property and pass RecyclerView.ViewHolder the root view (<layout> in list item xml replaced our root view)

        //binding.root is the <layout> tag from list_item xml

        fun bind(item: Cemetery){   //8. gives our cemetery <data> binding tag data to bind
            binding.cemetery = item
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