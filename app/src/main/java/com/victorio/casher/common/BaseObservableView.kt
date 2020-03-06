package com.victorio.casher.common

abstract class BaseObservableView<ListenerType> : BaseView(), ObservableMvc<ListenerType> {

    protected var listenersList = arrayListOf<ListenerType>()

    override fun registerListener(listener: ListenerType) {
        listenersList.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        listenersList.remove(listener)
    }
}