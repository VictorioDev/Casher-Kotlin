package com.victorio.casher.login

import com.victorio.casher.common.BaseObservableView
import com.victorio.casher.network.LoginRequestBody
import kotlinx.android.synthetic.main.activity_login_activity.view.*

class LoginMvcImpl : BaseObservableView<LoginViewMvc.Listener>(), LoginViewMvc {

    override fun setupView() {
        rootView.loginButton.setOnClickListener {
            onLoginButtonClicked()
        }
    }

    override fun navigateTo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSucess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginButtonClicked() {
        listenersList.forEach { listener ->
            listener.onLoginButtonClicked(
                LoginRequestBody("victorio@gmail.com", "123456")
            )
        }
    }
}

