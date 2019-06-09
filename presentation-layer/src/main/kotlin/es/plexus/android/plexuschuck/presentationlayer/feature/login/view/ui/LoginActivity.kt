package es.plexus.android.plexuschuck.presentationlayer.feature.login.view.ui

import androidx.fragment.app.FragmentActivity
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmActivity
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state.LoginState
import es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel.LoginActivityViewModel

class LoginActivity : BaseMvvmActivity<LoginActivityViewModel, LoginDomainLayerBridge, LoginState>() {

    override fun getViewModelClass(): Class<LoginActivityViewModel> = LoginActivityViewModel::class.java

    override fun getViewInstance(): FragmentActivity = this
}