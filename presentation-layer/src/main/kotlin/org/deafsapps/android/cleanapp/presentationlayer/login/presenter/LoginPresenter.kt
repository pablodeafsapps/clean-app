package org.deafsapps.android.cleanapp.presentationlayer.login.presenter

import org.deafsapps.android.cleanapp.domainlayer.feature.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter, KoinComponent {

    private val loginDomainLayerBridge: LoginDomainLayerBridge by inject("loginDomainLayerBridge")

    override fun onAttach(mvpView: LoginContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onButtonClicked(action: LoginContract.Action, email: String?, password: String?) {
        when (action) {
            LoginContract.Action.LOGIN -> loginUserWithData()
            LoginContract.Action.REGISTER -> TODO()
        }
    }

    private fun loginUserWithData() {
        loginDomainLayerBridge.loginUser()
    }

    override fun onToggleModeTapped(isLoginMode: Boolean) {
        if (isLoginMode) {
            view?.showRegisterUi()
        } else {
            view?.showLoginUi()
        }
    }

    override fun onDetach() {
        view = null
    }

}