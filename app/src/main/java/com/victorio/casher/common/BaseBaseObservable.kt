package com.victorio.casher.common

abstract class BaseBaseObservable<ListenerType> {


    protected var listenersList = arrayListOf<ListenerType>()

    fun registerListener(listener: ListenerType) {
        listenersList.add(listener)
    }

    fun unregisterListener(listener: ListenerType) {
        listenersList.remove(listener)
    }
}