package org.deafsapps.android.cleanapp.presentationlayer.base

interface MvpPresenter<V : MvpView> {

    fun onAttach(mvpView: V)
    fun onDetach()

}