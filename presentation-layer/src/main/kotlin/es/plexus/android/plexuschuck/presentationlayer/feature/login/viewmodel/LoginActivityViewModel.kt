package es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.plexus.android.plexuschuck.domainlayer.base.FailureBo
import es.plexus.android.plexuschuck.domainlayer.feature.login.LOGIN_DOMAIN_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.boToVoFailure
import es.plexus.android.plexuschuck.presentationlayer.feature.login.LoginContract.Action
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext

class LoginActivityViewModel : BaseMvvmViewModel<LoginDomainLayerBridge<List<String?>, Boolean>, LoginState>() {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val loginDomainLayerBridge: LoginDomainLayerBridge<List<String?>, Boolean>
            by inject("loginDomainLayerBridge")

    private lateinit var _loginState: MutableLiveData<ScreenState<LoginState>>

    override val screenState: LiveData<ScreenState<LoginState>>
        get() {
            if (!::_loginState.isInitialized) {
                _loginState = MutableLiveData()
            }
            return _loginState
        }

    override fun getDomainLayerBridgeId(): String = LOGIN_DOMAIN_TAG

    fun onButtonClicked(action: Action, email: String?, password: String?) {
        _loginState.value = ScreenState.Render(LoginState.Loading)
        when (action) {
            Action.LOGIN -> loginUserWithData(email, password)
            Action.REGISTER -> registerUserWithData(email, password)
        }
    }

    fun onToggleModeTapped(isLoginMode: Boolean) {
        _loginState.value =
            if (isLoginMode) ScreenState.Render(LoginState.Register) else ScreenState.Render(LoginState.Login)
    }

    private fun loginUserWithData(email: String?, password: String?) {
        loginDomainLayerBridge.loginUser(scope = this, params = listOf(email, password),
            onResult = {
                it.either(::handleError, ::handleSuccess)
                _loginState.value = ScreenState.Render(LoginState.Loading)
            })
    }

    private fun registerUserWithData(email: String?, password: String?) {
        loginDomainLayerBridge.registerUser(scope = this, params = listOf(email, password),
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