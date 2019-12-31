package com.victorio.casher.data

import android.util.Base64

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.victorio.casher.network.NetworkDataSourceImpl



class LoginViewModel: ViewModel(){



    private var state : MutableLiveData<Resource> = MutableLiveData()
    private var networkDataSourceImpl : NetworkDataSourceImpl = NetworkDataSourceImpl()


    fun login(email: String = "victorio@gmail.com", password: String = "123"){
        state.postValue(Resource(State.LOADING))
        networkDataSourceImpl.login(email, password, LoginListenerImpl(state))
    }
    


    fun getState(): LiveData<Resource> {
        return state
    }



}