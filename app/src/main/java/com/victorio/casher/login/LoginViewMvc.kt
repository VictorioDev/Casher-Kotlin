package com.victorio.casher.login
import com.victorio.casher.common.BaseObservableInterface
import com.victorio.casher.common.BaseObservableViewInterface
import com.victorio.casher.network.LoginRequestBody

interface LoginViewMvc : BaseObservableViewInterface<LoginViewMvc.Listener>{

    fun onLoginButtonClicked()
    fun onRegisterButtonClicked()
    fun showLoadingContainer()
    fun hideLoadingContainer()
    fun hideKeyboard()

    interface Listener {
        fun onLoginButtonClicked(loginRequestBody: LoginRequestBody)
        fun onRegisterButtonClicked()
    }

}