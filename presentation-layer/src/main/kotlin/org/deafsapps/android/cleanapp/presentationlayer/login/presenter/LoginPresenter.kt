package org.deafsapps.android.cleanapp.presentationlayer.login.presenter

import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract
import org.deafsapps.android.cleanapp.presentationlayer.domain.boToVoFailure
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter, KoinComponent {

    private val loginDomainLayerBridge: LoginDomainLayerBridge<List<String?>, Boolean> by inject("loginDomainLayerBridge")

    override fun onAttach(mvpView: LoginContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onViewResumed() {
        view?.clearTextFields()
    }

    override fun onButtonClicked(action: LoginContract.Action, email: String?, password: String?) {
        when (action) {
            LoginContract.Action.LOGIN -> loginUserWithData(email, password)
            LoginContract.Action.REGISTER -> registerUserWithData(email, password)
        }
        view?.showLoading()
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

    private fun loginUserWithData(email: String?, password: String?) {
        loginDomainLayerBridge.loginUser(params = listOf(email, password),
            onResult = {
                it.either(::handleError, ::handleSuccess)
                view?.hideLoading()
            })
    }

    private fun registerUserWithData(email: String?, password: String?) {
        loginDomainLayerBridge.registerUser(params = listOf(email, password),
            onResult = {
                it.either(::handleError, ::handleSuccess)
                view?.hideLoading()
            })
    }

    private fun handleSuccess(isSuccessful: Boolean) {
        if (isSuccessful) {
            view?.navigateToMainActivity()
        }
    }

    private fun handleError(failureBo: FailureBo) {
        view?.showInfoMessage(failureBo.boToVoFailure().getErrorMessage())
    }

}