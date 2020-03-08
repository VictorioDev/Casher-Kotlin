package com.victorio.casher.dependencyinjection

import androidx.appcompat.app.AppCompatActivity
import com.victorio.casher.login.LoginControllerMvc
import com.victorio.casher.login.LoginViewMvcImpl
import com.victorio.casher.login.usecase.LoginUseCase
import com.victorio.casher.navigation.Navigator

class ControllerCompositionRoot(var mActivity: AppCompatActivity?) {

    fun getLoginViewMvc() = LoginViewMvcImpl(mActivity!!.layoutInflater)
    fun onDestroy() = run { mActivity = null }
    fun getLoginControllerMvc() = LoginControllerMvc(getLoginUseCase(), getNavigator())

    private fun getNavigator() = Navigator(mActivity!!.baseContext)
    private fun getLoginUseCase() = LoginUseCase()
}