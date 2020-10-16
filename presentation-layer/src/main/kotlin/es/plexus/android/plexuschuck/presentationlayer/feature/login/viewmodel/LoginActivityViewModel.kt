package es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel

import androidx.lifecycle.viewModelScope
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.R
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.UserLoginVo
import es.plexus.android.plexuschuck.presentationlayer.domain.boToVoFailure
import es.plexus.android.plexuschuck.presentationlayer.domain.voToBo
import es.plexus.android.plexuschuck.presentationlayer.feature.login.LoginContract.Action
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LoginActivityViewModel(bridge: LoginDomainLayerBridge<UserLoginBo, Boolean>) :
    BaseMvvmViewModel<LoginDomainLayerBridge<UserLoginBo, Boolean>, LoginState>(bridge = bridge) {

    fun onButtonSelected(action: Action, userData: UserLoginVo) {
        _screenState.value = ScreenState.Loading
        when (action) {
            Action.LOGIN -> loginUserWithData(userData = userData)
            Action.REGISTER -> registerUserWithData(userData = userData)
        }
    }

    fun onToggleModeTapped(isLoginMode: Boolean) {
        _screenState.value = ScreenState.Render(if (isLoginMode) LoginState.Register else LoginState.Login)
    }

    private fun loginUserWithData(userData: UserLoginVo) {
        bridge.loginUser(scope = viewModelScope, params = userData.voToBo(),
            onResult = {
                it.fold(::handleError, ::handleSuccess)
            })
    }

    private fun registerUserWithData(userData: UserLoginVo) {
        bridge.registerUser(scope = viewModelScope, params = userData.voToBo(),
            onResult = {
                it.fold(::handleError, ::handleSuccess)
            })
    }

    private fun handleSuccess(isSuccessful: Boolean) {
        _screenState.value = if (isSuccessful) {
            ScreenState.Render(LoginState.AccessGranted)
        } else {
            ScreenState.Render(LoginState.ShowError(failure = FailureVo.Unknown(msgRes = R.string.error_login_response)))
        }
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value = ScreenState.Render(LoginState.ShowError(failureBo.boToVoFailure()))
    }

}