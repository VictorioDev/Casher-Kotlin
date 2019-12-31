package com.victorio.casher.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.victorio.casher.R
import com.victorio.casher.entity.Movimentation
import com.victorio.casher.network.CasherService
import com.victorio.casher.network.MovimentationsResponse
import com.victorio.casher.network.NetworkDataSource
import com.victorio.casher.network.NetworkDataSourceImpl
import kotlinx.android.synthetic.main.activity_movimentations.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request


class MovimentationsActivity : AppCompatActivity() {


    var movimentations: ArrayList<Movimentation> = arrayListOf()
    lateinit var cService : CasherService

    private val currentContext = this

    private var userId : String? = null
    var mAdapter = MovimentationsAdapter(movimentations)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimentations)

        setSupportActionBar(movToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var balance = intent.extras?.get("balance")
        userId = intent.extras?.getString("user_id")

        balance.let { collapsing_toolbar?.title = "R$" + "%.2f".format(it) }

        cService = CasherService.getInstance()

        setupRecyclerView()

        fab_movi.setOnClickListener {
            val i = Intent(this, CreateMovimentationActivity::class.java)
            startActivity(i)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        getMovimentations()
    }

    private fun setupRecyclerView(){
        mAdapter = MovimentationsAdapter(movimentations)

        val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.movimentations_layout_animation)

        movRecyclerView?.apply {
            layoutManager = LinearLayoutManager(currentContext)
            adapter = mAdapter
            layoutAnimation = animation

        }
    }

    private fun getMovimentations(){

        Log.d("VAZP", "OnResume")
        CoroutineScope(Dispatchers.Main).launch {
            var response = withContext(Dispatchers.IO){
                Log.d("VAZP", "User: $userId")
                cService.getMovimentations(userId!!)
            }

            if(response.isSuccessful){
                var movimentationsResponse : MovimentationsResponse = Gson().fromJson<MovimentationsResponse>(response.body()?.string(), MovimentationsResponse::class.java)
                Log.d("VAZP","MovimentationsSize: ${movimentationsResponse.movimentations?.size}")
                movimentationsResponse.let {
                   movimentationsResponse ->
                    movimentationsResponse.movimentations?.forEach {
                        item ->
                        movimentations.add(item)
                        mAdapter.notifyItemInserted(movimentations.size - 1)
                    }

                }
            } else {
                Log.d("VAZP", "Deu ruimmmmmm!")
            }
        }
    }

}
