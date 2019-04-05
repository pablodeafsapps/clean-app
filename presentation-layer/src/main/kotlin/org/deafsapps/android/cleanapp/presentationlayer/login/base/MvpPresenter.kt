package org.deafsapps.android.cleanapp.presentationlayer.login.base

interface MvpPresenter<V : MvpView> {

    fun onAttach(mvpView: V)

    fun onDetach()

}