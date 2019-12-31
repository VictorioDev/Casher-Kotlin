package com.victorio.casher.ui

import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.github.vipulasri.timelineview.TimelineView
import com.victorio.casher.R
import com.victorio.casher.entity.Movimentation
import kotlinx.android.synthetic.main.movimentation_item.view.*
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random


class MovimentationsAdapter(var movimentations: List<Movimentation>) : RecyclerView.Adapter<MovimentationsAdapter.MovimentationsViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimentationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movimentation_item, parent, false)
        return MovimentationsViewHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return movimentations.size
    }

    override fun onBindViewHolder(holder: MovimentationsViewHolder, position: Int) {
        holder.bindView(position)
    }

    inner class MovimentationsViewHolder(var view: View, var viewType: Int) : RecyclerView.ViewHolder(view){

        private var mTimeLineView : TimelineView? = null
        fun bindView(position: Int){
            mTimeLineView = view.timeline
            mTimeLineView?.initLine(viewType)

            var message = ""


            var movimentation = movimentations[position]

            message += if(movimentation.type.equals("C", true)){
                "Crédito no valor de R$" + "%.2f".format(movimentation.value) + " reais."
            }else {
                "Débito no valor de R$" + "%.2f".format(movimentation.value) + " reais."
            }

            var day = movimentation.date.split(" ")[0].split("-")[2]
            var month = movimentation.date.split(" ")[0].split("-")[1]

            view.text_timeline_date.text = "$day ${getMonthInString(month)}"
            view.text_timeline_title.text = "$message"

        }

        private fun getMonthInString(num: String) : String {
            return when(num) {
                "01" -> "Jan"
                "02" -> "Fev"
                "03" -> "Mar"
                "04" -> "Abr"
                "05" -> "Mai"
                "06" -> "Jun"
                "07" -> "Jul"
                "08" -> "Ago"
                "09" -> "Set"
                "10" -> "Out"
                "11" -> "Nov"
                "12" -> "Dez"
                else -> ""
            }
        }
    }


}