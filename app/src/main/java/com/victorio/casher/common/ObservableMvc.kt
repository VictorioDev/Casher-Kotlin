package com.victorio.casher.common

interface ObservableMvc<ListenerType> {

    fun registerListener(listener: ListenerType)

    fun unregisterListener(listener: ListenerType)

}