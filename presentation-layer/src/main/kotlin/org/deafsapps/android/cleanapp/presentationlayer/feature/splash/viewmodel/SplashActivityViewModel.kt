package org.deafsapps.android.cleanapp.presentationlayer.feature.splash.viewmodel

import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.feature.splash.view.state.SplashState
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This [BaseMvvmViewModel] handles the 'splash' feature view-model. Therefore, it is in charge of
 * the logic which makes the app loads all the necessary data for a proper performance. It uses no
 * bridge whatsoever.
 *
 * All results update an observable variable, [_screenState], with [SplashState] values.
 */
@ExperimentalCoroutinesApi
class SplashActivityViewModel(bridge: BaseDomainLayerBridge.None, navigator: SplashNavigator) :
    BaseMvvmViewModel<BaseDomainLayerBridge.None, SplashNavigator, SplashState>(
        bridge = bridge,
        navigator = navigator
    ) {

    /**
     * Indicates that the associated view has been created
     */
    fun onViewCreated() {
        _screenState.value = ScreenState.Render(SplashState.LoadingFinished)
    }

    /**
     * Navigate to login activity and close this
     */
    fun startLoginActivityAndCloseThis() {
        navigator.startLoginAndCloseThis()
    }

}
