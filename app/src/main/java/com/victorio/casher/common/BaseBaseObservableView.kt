package com.victorio.casher.common

abstract class BaseBaseObservableView<ListenerType> : BaseView(),
    BaseObservableViewInterface<ListenerType> {

    protected var listenersList = arrayListOf<ListenerType>()

    override fun registerListener(listener: ListenerType) {
        listenersList.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        listenersList.remove(listener)
    }
}