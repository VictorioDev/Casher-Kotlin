package com.victorio.casher.data

data class Resource(var state: State, var message: String? = null)

enum class State {
    LOADING, ERROR, SUCESS
}