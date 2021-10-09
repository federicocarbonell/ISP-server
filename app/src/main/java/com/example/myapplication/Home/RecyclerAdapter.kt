package com.example.myapplication.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Models.Task

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    //CARGAR EL ARRAY//
    val titles = arrayOf("Heladera Samsung", "Heladera Panasonic")
    val desc = arrayOf("18 de julio esq.Andes \n 10:00hs", "21 de setiembre 2876 \\n 15:00hs\"")
    ///////////////////


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        //Cargar el array con los datos.
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDescription.text = desc[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView
        var itemDescription: TextView

        init {
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDescription = itemView.findViewById(R.id.item_description)
        }
    }
}