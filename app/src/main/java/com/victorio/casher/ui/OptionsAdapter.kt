package com.victorio.casher.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victorio.casher.R
import com.victorio.casher.entity.Option
import kotlinx.android.synthetic.main.options_item.view.*

class OptionsAdapter(var context: Context ) : RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

    private val options = arrayListOf(
        Option("Movimentations", R.drawable.ic_attach_money_black_24dp),
        Option("Categories", R.drawable.ic_bookmark_black_24dp),
        Option("Tags", R.drawable.ic_label_black_24dp)
        )

    private var onOptionClickListener: OnOptionClickListener? = null

    fun setOnOptionClickListener(onOptionClickListener: OnOptionClickListener){
        this.onOptionClickListener = onOptionClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.options_item, parent, false)
        return OptionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position])
    }

    inner class OptionViewHolder(var view: View): RecyclerView.ViewHolder(view){


        fun bind(option: Option){
            view.option_name.text = option.name
            view.option_img.setImageResource(option.imgSource)

            view.setOnClickListener {
                onOptionClickListener?.onClick(option)
            }
        }
    }
}