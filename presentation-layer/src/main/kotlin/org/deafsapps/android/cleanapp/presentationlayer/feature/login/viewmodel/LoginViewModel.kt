package org.deafsapps.android.cleanapp.presentationlayer.feature.login.viewmodel

import androidx.lifecycle.viewModelScope
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.UserLoginVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.cleanapp.presentationlayer.domain.voToBo
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.LoginContract.Action
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.view.state.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This [BaseMvvmViewModel] handles the 'login' feature view-model. Therefore, it is in charge of
 * the logic which allows a user to log-in and/or register. It uses a [LoginDomainLayerBridge] which
 * gathers all the operations available for this entity.
 *
 * All results update an observable variable, [_screenState], with [LoginState] values.
 */
@ExperimentalCoroutinesApi
class LoginViewModel(
    bridge: LoginDomainLayerBridge<UserLoginBo>,
    navigator: LoginNavigator
) : BaseMvvmViewModel<LoginDomainLayerBridge<UserLoginBo>, LoginNavigator, LoginState>(
    bridge = bridge, navigator = navigator
) {

    /**
     * Indicates that the associated view has been created
     */
    fun onViewCreated() {
        bridge.fetchSessionUser(
                scope = viewModelScope,
                onResult = {
                    it.fold({}) {
                        handleSuccess(true)
                    }
                })
    }

    /**
     * Represents a user interaction, particularly a button click or tap. According to the [Action]
     * input argument, an operation is invoked either for 'login' or 'register'.
     *
     * @param [action] - the purpose of this event
     * @param [userData] - the user data to be used
     */
    fun onButtonSelected(action: Action, userData: UserLoginVo) {
        _screenState.value = ScreenState.Loading
        when (action) {
            Action.LOGIN -> loginUserWithData(userData = userData)
            Action.REGISTER -> registerUserWithData(userData = userData)
        }
    }

    /**
     * Represents a user interaction, particularly a tab toggle displaying a new init mode, either
     * 'login' or 'register'.
     *
     * @param [isLoginMode] - a flag indicating whether the actual mode is 'login' or not
     */
    fun onToggleModeTapped(isLoginMode: Boolean) {
        _screenState.value =
            ScreenState.Render(if (isLoginMode) LoginState.Register else LoginState.Login)
    }

    /**
     * Navigate to Login activity and close the current
     */
    fun navigateToMainActivity() {
        navigator.navigateToMainAndCloseThis()
    }

    private fun loginUserWithData(userData: UserLoginVo) {
        bridge.loginUser(
            scope = viewModelScope, params = userData.voToBo(),
            onResult = {
                it.fold(::handleError, ::handleSuccess)
            }
        )
    }

    private fun registerUserWithData(userData: UserLoginVo) {
        bridge.registerUser(
            scope = viewModelScope, params = userData.voToBo(),
            onResult = {
                it.fold(::handleError, ::handleSuccess)
            }
        )
    }

    private fun handleSuccess(isSuccessful: Boolean) {
        _screenState.value = if (isSuccessful) {
            ScreenState.Render(LoginState.AccessGranted)
        } else {
            ScreenState.Render(LoginState.ShowError(failure = FailureVo.Unknown))
        }
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value = ScreenState.Render(LoginState.ShowError(failureBo.boToVoFailure()))
    }

}
