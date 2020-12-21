package org.deafsapps.android.cleanapp.presentationlayer.splash

import org.deafsapps.android.cleanapp.presentationlayer.base.MvpPresenter
import org.deafsapps.android.cleanapp.presentationlayer.base.MvpView

interface SplashContract {

    interface View : MvpView {
        fun finishView()
    }

    interface Presenter : MvpPresenter<View> {
        fun onViewCreated()
    }

}