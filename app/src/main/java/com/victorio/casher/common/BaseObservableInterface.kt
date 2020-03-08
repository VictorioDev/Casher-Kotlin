package com.victorio.casher.common

interface BaseObservableInterface<ListenerType> {

    fun registerListener(listener: ListenerType)

    fun unregisterListener(listener: ListenerType)

}