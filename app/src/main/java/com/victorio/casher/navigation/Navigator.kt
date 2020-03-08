package com.victorio.casher.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Navigator(var context: Context) {

    fun navigateTo(targetClass: Class<*>, bundle: Bundle? = null) {
        var intent = Intent(context, targetClass)
        bundle?.let {
            intent.putExtras(it)
        }
        (context as AppCompatActivity).startActivity(intent)
    }


}