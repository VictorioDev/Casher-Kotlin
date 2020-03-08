package com.victorio.casher.login

import com.victorio.casher.common.BaseBaseObservable
import com.victorio.casher.common.ViewMvc
import com.victorio.casher.login.usecase.LoginUseCase
import com.victorio.casher.navigation.Navigator
import com.victorio.casher.network.LoginRequestBody
import com.victorio.casher.ui.RegisterActivity

class LoginControllerMvc(
    var loginUseCase: LoginUseCase,
    var navigator: Navigator) :
    LoginViewMvc.Listener,
    LoginUseCase.Listener{

    private lateinit var mViewMvc: LoginViewMvc


    fun onStart(){
        mViewMvc.registerListener(this)
    }

    fun onStop(){
        mViewMvc.unregisterListener(this)
    }

    override fun onLoginButtonClicked(loginRequestBody: LoginRequestBody) {
        mViewMvc.hideKeyboard()
        mViewMvc.showLoadingContainer()
        loginUseCase.doLoginAndNotify()
    }

    override fun onRegisterButtonClicked() {
       navigator.navigateTo(RegisterActivity::class.java)
    }

    override fun onLoginSuceed() {
        mViewMvc.hideLoadingContainer()
    }

    override fun onLoginError() {
        mViewMvc.hideLoadingContainer()
    }

    fun bindView(viewMvc: LoginViewMvc) {
        mViewMvc = viewMvc
    }
}