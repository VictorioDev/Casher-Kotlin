package com.victorio.casher.common

import android.view.View

abstract class BaseView {

    protected lateinit var rootView : View

    fun setView(view: View){
        rootView = view
        setupView()
    }

    abstract fun setupView()
}