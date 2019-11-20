package com.victorio.casher.Utils

import android.util.Log

class Logger private constructor(){

    companion object{
        fun log(texto: String?) {
            Log.i("CasherApp",texto)
        }
    }
}