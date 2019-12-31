package com.victorio.casher.network

import com.victorio.casher.data.LoginListener

interface NetworkDataSource {

     fun login(email: String, password: String, loginListener: LoginListener)


}