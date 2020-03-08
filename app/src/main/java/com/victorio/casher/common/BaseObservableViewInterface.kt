package com.victorio.casher.common

interface BaseObservableViewInterface<Listenertype>: ViewMvc{

    fun registerListener(listener: Listenertype)

    fun unregisterListener(listener: Listenertype)

}