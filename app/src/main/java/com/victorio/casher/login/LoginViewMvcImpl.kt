package com.victorio.casher.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.victorio.casher.R
import com.victorio.casher.common.BaseBaseObservableView
import com.victorio.casher.network.LoginRequestBody
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login.view.loadingContainer
import kotlinx.android.synthetic.main.activity_register.view.*

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class LoginViewMvcImpl(var layoutInflater: LayoutInflater) :
    BaseBaseObservableView<LoginViewMvc.Listener>(),
    LoginViewMvc {


    init {
        setView(layoutInflater.inflate(R.layout.activity_login, null, false))
    }

    override fun setupViews() {
        getView().loginButton.setOnClickListener {
            onLoginButtonClicked()
        }

        getView().registerButton.setOnClickListener {
            onRegisterButtonClicked()
        }

    }

    override fun showLoadingContainer() {
        getView().loadingContainer.visibility = View.VISIBLE
    }

    override fun hideLoadingContainer() {
        getView().loadingContainer.visibility = View.INVISIBLE
    }


    override fun onRegisterButtonClicked() {
        listenersList.forEach { listener ->
            listener.onRegisterButtonClicked()
        }
    }


    override fun onLoginButtonClicked() {
        listenersList.forEach { listener ->
            listener.onLoginButtonClicked(
                LoginRequestBody("victorio@gmail.com", "123456")
            )
        }
    }

    override fun hideKeyboard(){
        UIUtil.hideKeyboard(layoutInflater.context as AppCompatActivity)
    }
}

