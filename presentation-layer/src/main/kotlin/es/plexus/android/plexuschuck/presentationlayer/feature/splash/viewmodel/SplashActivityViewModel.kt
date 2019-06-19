package es.plexus.android.plexuschuck.presentationlayer.feature.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.plexus.android.plexuschuck.domainlayer.feature.splash.SPLASH_DOMAIN_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.splash.SplashDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.state.SplashState

class SplashActivityViewModel : BaseMvvmViewModel<SplashDomainLayerBridge, SplashState>() {

    override val bridge: SplashDomainLayerBridge? = null   // no 'domain-layer' needed
    private lateinit var _splashState: MutableLiveData<ScreenState<SplashState>>
    override val screenState: LiveData<ScreenState<SplashState>>
        get() {
            if (!::_splashState.isInitialized) {
                _splashState = MutableLiveData()
            }
            return _splashState
        }

    override fun getDomainLayerBridgeId(): String = SPLASH_DOMAIN_TAG

    fun onViewCreated() {
        _splashState.value = ScreenState.Render(SplashState.LoadingFinished)
    }

}