package com.victorio.casher.ui


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.victorio.casher.R
import com.victorio.casher.entity.Movimentation
import com.victorio.casher.network.CasherService
import com.victorio.casher.network.MovimentationsResponse
import kotlinx.android.synthetic.main.activity_movimentation_filter_layout.*
import kotlinx.android.synthetic.main.activity_movimentations.*
import kotlinx.coroutines.*
import kotlin.math.hypot


class MovimentationsActivity : AppCompatActivity() {


    var movimentations: ArrayList<Movimentation> = arrayListOf()
    lateinit var cService: CasherService

    private val currentContext = this

    private var userId: String? = null
    var mAdapter = MovimentationsAdapter(movimentations)

    var viewStub: View? = null


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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movimentations_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter) {
            openCircularRevealAnimation()
            return true
        }

        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        getMovimentations()
    }

    private fun setupRecyclerView() {
        mAdapter = MovimentationsAdapter(movimentations)

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(this, R.anim.movimentations_layout_animation)

        movRecyclerView?.apply {
            layoutManager = LinearLayoutManager(currentContext)
            adapter = mAdapter
            layoutAnimation = animation

        }
    }

    private fun openCircularRevealAnimation() {


        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //val cx = mov_frame.width?.div(2)
            //val cy = mov_frame.height?.div(2)
            val cx = mov_frame.right
            val cy = mov_frame.top
            // get the final radius for the clipping circle
            val finalRadius =
                hypot(mov_frame.width.toDouble(), mov_frame.height.toDouble()).toFloat()
            // create the animator for this view (the start radius is zero)
            if (viewStub == null) {
                viewStub = filter_container.inflate()

            }else {
                viewStub!!.visibility = View.VISIBLE


            }



            btn_close.setOnClickListener {
                closeCircularRevealAnimation()
            }

            val anim = ViewAnimationUtils.createCircularReveal(viewStub, cx, cy, 0f, finalRadius)
            anim.duration = 350
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    var finalY = pill_container.y
                    var offsetY = finalY + pill_container.height

                    var animSet = AnimatorSet()

                    var pillContainerAnimation =
                        ObjectAnimator.ofFloat(pill_container, "translationY", offsetY, 0f)

                    pillContainerAnimation.apply {
                        duration = 370
                        interpolator = AccelerateDecelerateInterpolator()
                        doOnStart {
                            pill_container.visibility = View.VISIBLE
                        }
                    }


                    var closeButtonAnimation = ObjectAnimator.ofFloat(btn_close, "alpha", 0F, 1F)
                    closeButtonAnimation.duration = 300
                    closeButtonAnimation.doOnStart {
                        btn_close.visibility = View.VISIBLE
                    }



                    animSet.playTogether(pillContainerAnimation, closeButtonAnimation)
                    animSet.start()


                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }
            })
            // make the view visible and start the animation

            anim.start()
            Log.d("VAZP", "Entrou aqui!")

            // get the center for the clipping circle

        } else {
            // set the view to invisible without a circular reveal animation below Lollipop

        }

    }

    private fun closeCircularRevealAnimation() {
        viewStub.let {

            var offsetY = pill_container.y + pill_container.height
            var animSet = AnimatorSet()
            /* pill_container
                 .animate()
                 .translationY((pill_container.height).toFloat())
                 .setDuration(350)
                 .setInterpolator(AccelerateDecelerateInterpolator())
                 .start()*/

            var pillContainerAnimation =
                ObjectAnimator.ofFloat(pill_container, "translationY", 0f, offsetY)

            pillContainerAnimation.apply {
                duration = 350
                interpolator = AccelerateDecelerateInterpolator()
                doOnEnd {
                    pill_container.visibility = View.INVISIBLE
                }
            }


            var closeButtonAnimation = ObjectAnimator.ofFloat(btn_close, "alpha", 1F, 0F)
            closeButtonAnimation.duration = 300
            closeButtonAnimation.doOnEnd {
                btn_close.visibility = View.INVISIBLE
            }

            animSet.playTogether(pillContainerAnimation, closeButtonAnimation)

            animSet.doOnEnd {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //val cx = mov_frame.width?.div(2)
                    //val cy = mov_frame.height?.div(2)
                    val cx = mov_frame.right
                    val cy = mov_frame.top
                    // get the final radius for the clipping circle

                    val startRadius =
                        hypot(mov_frame.width.toDouble(), mov_frame.height.toDouble()).toFloat()



                    // create the animator for this view (the start radius is zero)

                    val anim =
                        ViewAnimationUtils.createCircularReveal(viewStub, cx, cy, startRadius, 0f  )
                    anim.apply {
                        duration = 500
                        anim.doOnEnd {
                            viewStub!!.visibility = View.INVISIBLE
                        }
                    }
                    anim.start()


                }


            }
            animSet.start()
        }
    }

    private fun getMovimentations() {

        Log.d("VAZP", "OnResume")
        CoroutineScope(Dispatchers.Main).launch {
            var response = withContext(Dispatchers.IO) {
                Log.d("VAZP", "User: $userId")
                cService.getMovimentations(userId!!)
            }

            if (response.isSuccessful) {
                var movimentationsResponse: MovimentationsResponse =
                    Gson().fromJson<MovimentationsResponse>(
                        response.body()?.string(),
                        MovimentationsResponse::class.java
                    )
                Log.d("VAZP", "MovimentationsSize: ${movimentationsResponse.movimentations?.size}")
                movimentationsResponse.let { movimentationsResponse ->
                    movimentationsResponse.movimentations?.forEach { item ->
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
