package com.victorio.casher.login.usecase

import com.victorio.casher.common.BaseBaseObservable

class LoginUseCase : BaseBaseObservable<LoginUseCase.Listener>() {

    interface Listener {
        fun onLoginSuceed()
        fun onLoginError()
    }

    fun doLoginAndNotify(){

    }


    private fun onSucess(){
        TODO()
    }

    private fun onErrror(){
        TODO()
    }


}