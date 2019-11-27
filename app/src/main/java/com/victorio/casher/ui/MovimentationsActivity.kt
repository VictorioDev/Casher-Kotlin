package com.victorio.casher.ui


import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.victorio.casher.R
import kotlinx.android.synthetic.main.activity_movimentations.*


class MovimentationsActivity : AppCompatActivity() {

    private val currentContext = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimentations)

        setSupportActionBar(movToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var balance = intent.extras?.get("balance")

        balance.let { collapsing_toolbar?.title = "R$" + "%.2f".format(it) }


        setupRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupRecyclerView(){
        var mAdapter = MovimentationsAdapter()

        val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.movimentations_layout_animation)

        movRecyclerView?.apply {
            layoutManager = LinearLayoutManager(currentContext)
            adapter = mAdapter
            layoutAnimation = animation
        }
    }

}
