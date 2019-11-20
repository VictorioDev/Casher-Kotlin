package com.victorio.casher.data

import androidx.lifecycle.MutableLiveData

class LoginListenerImpl(private var state: MutableLiveData<Resource>) : LoginListener {

    override fun onError(message: String?) {
        state.postValue(Resource(State.ERROR, message))
    }

    override fun onSucess(message: String?) {
        state.postValue(Resource(State.SUCESS, message))
    }
}