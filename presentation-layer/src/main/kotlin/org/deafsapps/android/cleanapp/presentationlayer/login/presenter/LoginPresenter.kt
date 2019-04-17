package org.deafsapps.android.cleanapp.presentationlayer.login.presenter

import android.util.Log
import org.deafsapps.android.cleanapp.domainlayer.feature.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter, KoinComponent {

    private val loginDomainLayerBridge: LoginDomainLayerBridge by inject("loginDomainLayerBridge")

    override fun onAttach(mvpView: LoginContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onButtonClicked(action: LoginContract.Action, email: String?, password: String?) {
        when (action) {
            LoginContract.Action.LOGIN -> loginUserWithData(email, password)
            LoginContract.Action.REGISTER -> TODO()
        }
    }

    private fun loginUserWithData(email: String?, password: String?) {
        loginDomainLayerBridge.loginUser { result ->
            if (result.isRight) {
                view?.showInfoMessage("OK")
            } else {
                view?.showInfoMessage("ERROR")
            }
        }
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