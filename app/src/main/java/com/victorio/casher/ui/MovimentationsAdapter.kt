package com.victorio.casher.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.victorio.casher.R
import kotlinx.android.synthetic.main.movimentation_item.view.*
import kotlin.math.roundToInt
import kotlin.random.Random


class MovimentationsAdapter : RecyclerView.Adapter<MovimentationsAdapter.MovimentationsViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimentationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movimentation_item, parent, false)
        return MovimentationsViewHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MovimentationsViewHolder, position: Int) {
        holder.bindView(position)
    }

    inner class MovimentationsViewHolder(var view: View, var viewType: Int) : RecyclerView.ViewHolder(view){

        private var mTimeLineView : TimelineView? = null
        fun bindView(position: Int){
            mTimeLineView = view.timeline
            mTimeLineView?.initLine(viewType)

            var randomValue = (Math.random()*((5000-100)+1))+100
            var randomDay = (Math.random()*((1-30)+1))+30
            var creditOrDebit = Random.nextBoolean()
            var months = arrayListOf("Jan", "Fev", "Dez", "Set", "Jun")

            var message = ""

            message += if(creditOrDebit){
                "Crédito no valor de R$" + "%.2f".format(randomValue) + " reais."
            }else {
                "Débito no valor de R$" + "%.2f".format(randomValue) + " reais."
            }

            view.text_timeline_date.text = "${randomDay.roundToInt()} ${months[Random.nextInt(months.size)]}"
            view.text_timeline_title.text = "$message"
        }
    }


}