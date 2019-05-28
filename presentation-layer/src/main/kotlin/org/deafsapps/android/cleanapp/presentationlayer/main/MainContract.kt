package org.deafsapps.android.cleanapp.presentationlayer.main

import org.deafsapps.android.cleanapp.presentationlayer.base.MvpPresenter
import org.deafsapps.android.cleanapp.presentationlayer.base.MvpView
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo

interface MainContract {

    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showInfoMessage(msg: String)
        fun loadJokesData(data: List<JokeVo>)
        fun navigateToDetailActivity(item: JokeVo)
    }

    interface Presenter : MvpPresenter<View> {
        fun onViewResumed()
        fun onJokeItemClicked(item: JokeVo)
    }

}