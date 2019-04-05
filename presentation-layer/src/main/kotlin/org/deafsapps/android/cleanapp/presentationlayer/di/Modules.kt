package org.deafsapps.android.cleanapp.presentationlayer.di

import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.usecase.LoginUserApiUc
import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract
import org.deafsapps.android.cleanapp.presentationlayer.login.presenter.LoginPresenter
import org.koin.dsl.module.module

val presentationLayerModule = module(override = true) {
    factory<LoginContract.Presenter> { (view: LoginContract.View) ->
        LoginPresenter(view)
    }
    factory<DomainLayerContract.UseCase>("loginUserApiUc") { LoginUserApiUc() }
//    factory<DomainLayerContract>("registerUserApiUc") { RegisterUserApiUc() }
}