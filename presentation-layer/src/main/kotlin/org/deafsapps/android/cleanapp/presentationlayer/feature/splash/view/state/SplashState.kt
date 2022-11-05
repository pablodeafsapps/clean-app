package org.deafsapps.android.cleanapp.presentationlayer.feature.splash.view.state

import org.deafsapps.android.cleanapp.presentationlayer.base.BaseState

/**
 * Models all available states for the 'Splash' feature view
 */
sealed class SplashState : BaseState() {
    object LoadingFinished : SplashState()
}
