package com.victorio.casher.ui

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.victorio.casher.R
import com.victorio.casher.Utils.Logger
import com.victorio.casher.entity.Movimentation
import com.victorio.casher.entity.Option
import com.victorio.casher.entity.Summary
import com.victorio.casher.network.NetworkDataSourceImpl
import kotlinx.android.synthetic.main.activity_balance.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BalanceActivity : AppCompatActivity(), OnOptionClickListener {

    var optionsAdapter = OptionsAdapter(this)
    var currentContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        var userId = intent.getStringExtra("user_id")

        var mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var itemDivider = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        itemDivider.setDrawable(resources.getDrawable(R.drawable.option_divider))

        val resId: Int = R.anim.options_layout_animation
        val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, resId)

        optionsAdapter.setOnOptionClickListener(this)
        options_recycler.apply {
            adapter = optionsAdapter
            layoutManager = mLayoutManager
            addItemDecoration(itemDivider)
            layoutAnimation = animation
        }



        CoroutineScope(Dispatchers.Main).launch{


            var cService = NetworkDataSourceImpl().casherService

            var call = withContext(Dispatchers.IO){
                cService.getMovimentations("$userId").execute()
            }

            if(call.isSuccessful){
                loadingContainer.visibility = View.INVISIBLE
                var resp = call.body()?.string()
                Logger.log(resp)
                resp.let {


                    balance_container.startAnimation(AnimationUtils.loadAnimation(currentContext, R.anim.fade_in_balance)).also {
                        balance_container.visibility = View.VISIBLE
                    }
                    var gson = Gson()
                    var summary = gson.fromJson<Summary>(it, Summary::class.java)
                    tvBalanceValue.text = summary?.balance
                    if(summary.positive_balance){
                        tvRs.setTextColor(resources.getColor(android.R.color.holo_green_light))
                        tvBalanceValue.setTextColor(resources.getColor(android.R.color.holo_green_light))
                    }

                    if(summary?.last_entries?.size == 0) {
                        var lastEntry : Movimentation? = summary?.last_entries?.get(0)
                        tvLatestMovimentation.text = lastEntry?.name

                        if(lastEntry?.type.equals("D", true)){
                            ivLatestMovimentation.setImageResource(R.drawable.ic_arrow_downward_black_24dp)
                        }else {
                            ivLatestMovimentation.setImageResource(R.drawable.ic_arrow_upward_black_24dp)

                        }
                    }else {
                        tvLatestMovimentation.text = "You don't have movimentations yet"
                        ivLatestMovimentation.setImageResource(R.drawable.ic_info_outline_black_24dp)
                    }




                }
            }else {
                Logger.log("DEU ERRO:  ${call.raw().toString()}")
            }

        }
    }

    override fun onClick(option : Option) {
        //Toast.makeText(this, "Clickou no item ${option.name}", Toast.LENGTH_SHORT).show()
        when(option.name){
            "Movimentations" -> {
                var intent = Intent(this, MovimentationsActivity::class.java)
                startActivity(intent)
            }
        }
    }


}
