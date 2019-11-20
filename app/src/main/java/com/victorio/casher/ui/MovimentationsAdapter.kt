package com.victorio.casher.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victorio.casher.R


class MovimentationsAdapter : RecyclerView.Adapter<MovimentationsAdapter.MovimentationsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimentationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MovimentationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MovimentationsViewHolder, position: Int) {
        holder.bindView(position)
    }

    inner class MovimentationsViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private var textView = view as TextView
        fun bindView(position: Int){
            textView.text = "Movimentation $position"
        }
    }


}