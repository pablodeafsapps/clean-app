package org.deafsapps.android.cleanapp.presentationlayer.detail

import org.deafsapps.android.cleanapp.presentationlayer.base.MvpPresenter
import org.deafsapps.android.cleanapp.presentationlayer.base.MvpView
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo

interface DetailContract {

    interface View : MvpView {
        fun showLoading()
        fun hideLoading()
        fun showInfoMessage(msg: String)
        fun loadJokeItem(item: JokeVo)
    }

    interface Presenter : MvpPresenter<View> {
        fun onViewResumed(item: JokeVo?)
    }

}