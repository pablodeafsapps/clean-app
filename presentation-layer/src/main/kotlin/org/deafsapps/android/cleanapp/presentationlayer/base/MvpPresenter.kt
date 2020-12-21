package org.deafsapps.android.cleanapp.presentationlayer.base

import kotlinx.coroutines.CoroutineScope

interface MvpPresenter<V : MvpView> : CoroutineScope {

    fun onAttach(mvpView: V)
    fun onDetach()

}