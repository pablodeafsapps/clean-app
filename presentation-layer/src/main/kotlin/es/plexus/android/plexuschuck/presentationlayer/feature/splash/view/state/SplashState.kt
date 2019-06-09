package es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.state

import es.plexus.android.plexuschuck.presentationlayer.base.BaseState
import es.plexus.android.plexuschuck.presentationlayer.base.FailureVo

sealed class SplashState : BaseState() {
    object FinishView : SplashState()
}