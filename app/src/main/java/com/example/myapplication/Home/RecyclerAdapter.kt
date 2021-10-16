package com.example.myapplication.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Job
import com.example.myapplication.R
import com.example.myapplication.Models.Task

class RecyclerAdapter(private val dataSet: Array<Job>?): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        //Cuando tenga el array de objetos decirle que parte del objeto quiero mostrar.
        holder.itemTitle.text = dataSet?.get(position)?.product?.name
        holder.itemDescription.text = dataSet?.get(position)?.description
        holder.button.setTag( dataSet?.get(position)?.product?.name.toString())
        //holder.button.setTag(1, dataSet?.get(position)?.description.toString())
        //holder.button.setTag(2, dataSet?.get(position)?.state.toString())
        //holder.button.setTag(3, dataSet?.get(position)?.latitud.toString())
        //holder.button.setTag(4, dataSet?.get(position)?.longitude.toString())
    }

    override fun getItemCount(): Int {
        return dataSet?.size!!
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView
        var itemDescription: TextView
        var button: Button
        init {
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDescription = itemView.findViewById(R.id.item_description)
            button = itemView.findViewById(R.id.button)
        }
    }
}