package org.deafsapps.android.cleanapp.presentationlayer.login.presenter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext

class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter, KoinComponent {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val loginDomainLayerBridge: LoginDomainLayerBridge<List<String?>, Boolean> by inject("loginDomainLayerBridge")

    override fun onAttach(mvpView: LoginContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onViewResumed() {
        view?.clearTextFields()
    }

    override fun onButtonClicked(action: LoginContract.Action, email: String?, password: String?) {
        view?.showLoading()
        when (action) {
            LoginContract.Action.LOGIN -> loginUserWithData(email, password)
            LoginContract.Action.REGISTER -> registerUserWithData(email, password)
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
        job.cancel()
    }

    private fun loginUserWithData(email: String?, password: String?) {
        loginDomainLayerBridge.loginUser(scope = this, params = listOf(email, password),
            onResult = {
                it.either(::handleError, ::handleSuccess)
                view?.hideLoading()
            })
    }

    private fun registerUserWithData(email: String?, password: String?) {
        loginDomainLayerBridge.registerUser(scope = this, params = listOf(email, password),
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