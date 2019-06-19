package es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.state

import es.plexus.android.plexuschuck.presentationlayer.base.BaseState

sealed class SplashState : BaseState() {
    object LoadingFinished : SplashState()
}