package es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state

import es.plexus.android.plexuschuck.presentationlayer.base.BaseState
import es.plexus.android.plexuschuck.presentationlayer.base.FailureVo

sealed class LoginState : BaseState() {
    class ShowError(val failure: FailureVo?) : LoginState()
}