package com.victorio.casher.login
import com.victorio.casher.common.ObservableMvc
import com.victorio.casher.network.LoginRequestBody

interface LoginViewMvc {

    fun onLoginButtonClicked()

    interface Listener {
        fun onLoginButtonClicked(loginRequestBody: LoginRequestBody)
    }

    fun navigateTo()
    fun onSucess()
    fun onError()
}