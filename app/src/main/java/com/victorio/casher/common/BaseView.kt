package com.victorio.casher.common

import android.view.View

 abstract class BaseView : ViewMvc{

    private lateinit var rootView : View

     override fun setView(view: View) {
         rootView = view
         setupViews()
     }

     override fun getView() = rootView

     protected abstract fun setupViews()
 }