package org.deafsapps.android.cleanapp.presentationlayer.di

import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract
import org.deafsapps.android.cleanapp.presentationlayer.login.presenter.LoginPresenter
import org.deafsapps.android.cleanapp.presentationlayer.main.MainContract
import org.deafsapps.android.cleanapp.presentationlayer.main.presenter.MainPresenter
import org.deafsapps.android.cleanapp.presentationlayer.splash.SplashContract
import org.deafsapps.android.cleanapp.presentationlayer.splash.presenter.SplashPresenter
import org.koin.dsl.module.module

val presentationLayerModule = module(override = true) {
    factory<SplashContract.Presenter> { (view: SplashContract.View) ->
        SplashPresenter(view)
    }
    factory<LoginContract.Presenter> { (view: LoginContract.View) ->
        LoginPresenter(view)
    }
    factory<MainContract.Presenter> { (view: MainContract.View) ->
        MainPresenter(view)
    }
}