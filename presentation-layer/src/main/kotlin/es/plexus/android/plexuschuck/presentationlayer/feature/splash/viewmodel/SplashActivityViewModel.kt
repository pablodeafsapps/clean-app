package es.plexus.android.plexuschuck.presentationlayer.feature.splash.viewmodel

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.state.SplashState
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 *
 */
@ExperimentalCoroutinesApi
class SplashActivityViewModel(bridge: BaseDomainLayerBridge.None) :
    BaseMvvmViewModel<BaseDomainLayerBridge.None, SplashState>(bridge = bridge) {

    /**
     *
     */
    fun onViewCreated() {
        _screenState.value = ScreenState.Render(SplashState.LoadingFinished)
    }

}
