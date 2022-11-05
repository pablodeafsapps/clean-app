package org.deafsapps.android.cleanapp.presentationlayer.feature.login.view.state

import org.deafsapps.android.cleanapp.presentationlayer.base.BaseState
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo

/**
 * Models all available states for the 'Login' feature view
 */
sealed class LoginState : BaseState() {
    object Login : LoginState()
    object Logged : LoginState()
    object Register : LoginState()
    object AccessGranted : LoginState()
    class ShowError(val failure: FailureVo) : LoginState()
}
