package es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.feature.login.LOGIN_DOMAIN_BRIDGE_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.boToVoFailure
import es.plexus.android.plexuschuck.presentationlayer.feature.login.LoginContract.Action
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state.LoginState
import org.koin.core.inject
import org.koin.core.qualifier.named

class LoginActivityViewModel : BaseMvvmViewModel<LoginDomainLayerBridge<List<String?>, Boolean>, LoginState>() {

    override val bridge: LoginDomainLayerBridge<List<String?>, Boolean>? by inject(named(name = getDomainLayerBridgeId()))
    private lateinit var _loginState: MutableLiveData<ScreenState<LoginState>>
    override val screenState: LiveData<ScreenState<LoginState>>
        get() {
            if (!::_loginState.isInitialized) {
                _loginState = MutableLiveData()
            }
            return _loginState
        }

    override fun getDomainLayerBridgeId(): String = LOGIN_DOMAIN_BRIDGE_TAG

    fun onButtonClicked(action: Action, email: String?, password: String?) {
        _loginState.value = ScreenState.Loading
        when (action) {
            Action.LOGIN -> loginUserWithData(email, password)
            Action.REGISTER -> registerUserWithData(email, password)
        }
    }

    fun onToggleModeTapped(isLoginMode: Boolean) {
        _loginState.value =
            if (isLoginMode) ScreenState.Render(LoginState.Register) else ScreenState.Render(
                LoginState.Login
            )
    }

    private fun loginUserWithData(email: String?, password: String?) {
        bridge?.loginUser(scope = this, params = listOf(email, password),
            onResult = {
                it.either(::handleError, ::handleSuccess)
                _loginState.value = ScreenState.Render(LoginState.Idle)
            })
    }

    private fun registerUserWithData(email: String?, password: String?) {
        bridge?.registerUser(scope = this, params = listOf(email, password),
            onResult = {
                it.either(::handleError, ::handleSuccess)
                _loginState.value = ScreenState.Render(LoginState.Idle)
            })
    }

    private fun handleSuccess(isSuccessful: Boolean) {
        if (isSuccessful) {
            _loginState.value = ScreenState.Render(LoginState.AccessGranted)
        }
    }

    private fun handleError(failureBo: FailureBo) {
        _loginState.value = ScreenState.Render(LoginState.ShowError(failureBo.boToVoFailure()))
    }

}