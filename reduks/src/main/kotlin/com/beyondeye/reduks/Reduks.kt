package com.beyondeye.reduks

import com.beyondeye.reduks.modules.MultiStore
import com.beyondeye.reduks.modules.ReduksContext
import com.beyondeye.reduks.modules.subStore_

/**
 * Redux module containing all Reduks components: store and its (main) subscriber
 * Created by daely on 6/8/2016.
 */
interface Reduks<State> {
    val ctx: ReduksContext
    val store: Store<State>
    /**
     * the main store subscriber or null if none is defined
     */
    val storeSubscriber: StoreSubscriber<State>?
    /**
     * the subscription for the main store subscriber or null if none is defined
     */
    val storeSubscription: StoreSubscription?
}


/**
 * a shortcut for getting the current state from the store object
 */
val <S>Reduks<S>.state:S get()=store.state

/**
 * a shortcurt for calling dispatch on the store object
 */
fun <S>Reduks<S>.dispatch(action:Any) = store.dispatch(action)


/**
 * see [Store.subStore]
 */
inline fun<reified SB:Any> Reduks<*>.subStore(subctx:ReduksContext?=null):Store<SB>?  =store.subStore(subctx)
/**
 * see [Store.subState]
 */
inline fun<reified SB:Any> Reduks<*>.subState(subctx:ReduksContext?=null):SB?  =store.subState(subctx)
/**
 * see [Store.subDispatcher]
 */
inline fun<reified SB:Any> Reduks<*>.subDispatcher(subctx:ReduksContext?=null):((Any)->Any)?  =store.subDispatcher<SB>(subctx)



/**
 * try to retrieve a substore data: if input param subctx is null then
 * then use as context the  default substore ReduksContext for the specified state type SB
 * see [MultiStore.subStore_]
 * WARNING: when using the default context these methods use reflection
 * @return null if either the a substore with the specified context was not found or it was found but it has a
 * different substate type than required
 */
inline fun<reified SB:Any> Store<*>.subStore(subctx:ReduksContext?=null):Store<SB>?  =
    if(this is MultiStore)  subStore_<SB>(subctx) else null

/**
 * try to retrieve a substore state: if input param subctx is null then
 * then use as context the  default substore ReduksContext for the specified state type SB
 * see [MultiStore.subStore_]
 * WARNING: when using the default context these methods use reflection
 * @return null if either the a substore with the specified context was not found or it was found but it has a
 * different substate type than required
 */
inline fun<reified SB:Any> Store<*>.subState(subctx:ReduksContext?=null):SB?  =
    if(this is MultiStore)  subStore_<SB>(subctx)?.state else null


/**
 * try to retrieve a substore dispatcher: if input param subctx is null then
 * then use as context the  default substore ReduksContext for the specified state type SB
 * see [MultiStore.subStore_]
 * WARNING: when using the default context these methods use reflection
 * @return null if either the a substore with the specified context was not found or it was found but it has a
 * different substate type than required
 */
inline fun<reified SB:Any> Store<*>.subDispatcher(subctx:ReduksContext?=null):((Any)->Any)?  =
    if(this is MultiStore)  subStore_<SB>(subctx)?.dispatch else null

