package es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel

import androidx.lifecycle.LiveData
import es.plexus.android.plexuschuck.domainlayer.feature.login.LOGIN_DOMAIN_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state.LoginState

class LoginActivityViewModel : BaseMvvmViewModel<LoginDomainLayerBridge, LoginState>() {

    override val screenState: LiveData<ScreenState<LoginState>>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun getDomainLayerBridgeId(): String = LOGIN_DOMAIN_TAG

}