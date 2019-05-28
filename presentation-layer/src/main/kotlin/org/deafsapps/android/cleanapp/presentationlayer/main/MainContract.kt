package org.deafsapps.android.cleanapp.presentationlayer.main

import org.deafsapps.android.cleanapp.presentationlayer.base.MvpPresenter
import org.deafsapps.android.cleanapp.presentationlayer.base.MvpView

interface MainContract {

    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showInfoMessage(msg: String)
        fun navigateToDetailActivity()
    }

    interface Presenter : MvpPresenter<View> {
        fun onViewResumed()
        fun onJokeItemClicked(jokeItem: String?)
    }

}