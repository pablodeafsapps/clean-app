package org.deafsapps.android.cleanapp.presentationlayer.splash.presenter

import org.deafsapps.android.cleanapp.presentationlayer.splash.SplashContract
import org.koin.standalone.KoinComponent

class SplashPresenter(private var view: SplashContract.View?) : SplashContract.Presenter, KoinComponent {

    override fun onAttach(mvpView: SplashContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onViewCreated() {
        view?.finishView()
    }

    override fun onDetach() {
        view = null
    }

}