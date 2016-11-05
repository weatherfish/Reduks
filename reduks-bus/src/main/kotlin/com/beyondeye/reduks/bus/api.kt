package com.beyondeye.reduks.bus

import com.beyondeye.reduks.Reduks
import com.beyondeye.reduks.Store
import com.beyondeye.reduks.StoreSubscription

/**
 * Created by daely on 10/6/2016.
 */
//-------
inline fun <reified BusDataType:Any> Store<out StateWithBusData>.busData(key:String?=null):BusDataType? {
    if(this !is BusStore<*>) return null
    return this.state.busData[key?: BusDataType::class.java.name] as BusDataType?
}
inline fun <reified BusDataType:Any> Reduks<out StateWithBusData>.busData(key:String?=null):BusDataType?=store.busData(key)
//-------
inline fun <reified BusDataType:Any> Store<out StateWithBusData>.clearBusData(key:String?=null) {dispatch(ActionClearBusData(key?: BusDataType::class.java.name)) }
inline fun <reified BusDataType:Any> Reduks<out StateWithBusData>.clearBusData(key:String?=null) { store.clearBusData<BusDataType>(key) }
//-------
fun <BusDataType :Any> Store<out StateWithBusData>.postBusData(data: BusDataType, key:String?=null) { dispatch(ActionSendBusData(key ?: data.javaClass.name,data)) }
fun <BusDataType :Any> Reduks<out StateWithBusData>.postBusData(data: BusDataType, key:String?=null) { store.postBusData(data,key) }
//-------
fun Store<out StateWithBusData>.unsubscribeAllBusDataHandlers() {
    if(this is BusStore<*>) this.unsubscribeAllBusDataHandlers()
}
fun Reduks<out StateWithBusData>.unsubscribeAllBusDataHandlers() { store.unsubscribeAllBusDataHandlers()}
//--------
fun Store<out StateWithBusData>.removeBusDataHandler(subscription: StoreSubscription) {
    if(this is BusStore<*>) this.removeBusDataHandler(subscription)
}
fun Reduks<out StateWithBusData>.removeBusDataHandler(subscription: StoreSubscription) { store.removeBusDataHandler(subscription) }

fun Reduks<out StateWithBusData>.removeBusDataHandlers(subscriptions: MutableList<StoreSubscription>?) {
    subscriptions?.forEach { store.removeBusDataHandler(it) }
    subscriptions?.clear()
}

//--------
inline fun <reified BusDataType:Any> Store<out StateWithBusData>.addBusDataHandler(key:String?=null, noinline fn: (bd: BusDataType?) -> Unit) : StoreSubscription?{
    if(this is BusStore<*>) return this.addBusDataHandler<BusDataType>(key?: BusDataType::class.java.name,fn)
    else return null
}
inline fun <reified BusDataType:Any> Reduks<out StateWithBusData>.addBusDataHandler(key:String?=null, noinline fn: (bd: BusDataType?) -> Unit) : StoreSubscription?=
        store.addBusDataHandler(key,fn)
