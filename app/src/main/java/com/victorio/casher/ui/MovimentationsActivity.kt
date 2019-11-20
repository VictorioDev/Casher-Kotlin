package com.victorio.casher.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.victorio.casher.R
import kotlinx.android.synthetic.main.activity_movimentations.*

class MovimentationsActivity : AppCompatActivity() {

    private val currentContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimentations)

        setSupportActionBar(movToolbar)
        //supportActionBar?.title = ""
        collapsing_toolbar?.title = "R$1245,50"

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        var mAdapter = MovimentationsAdapter()

        movRecyclerView?.apply {
            layoutManager = LinearLayoutManager(currentContext)
            adapter = mAdapter
        }
    }
}
