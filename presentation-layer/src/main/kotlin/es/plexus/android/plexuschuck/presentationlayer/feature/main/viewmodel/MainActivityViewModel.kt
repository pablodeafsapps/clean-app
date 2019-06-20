package es.plexus.android.plexuschuck.presentationlayer.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import es.plexus.android.plexuschuck.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.main.MainDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.plexus.android.plexuschuck.presentationlayer.domain.boToVo
import es.plexus.android.plexuschuck.presentationlayer.domain.boToVoFailure
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.state.MainState
import org.koin.standalone.inject

class MainActivityViewModel : BaseMvvmViewModel<MainDomainLayerBridge<List<String>?, List<JokeBo>>, MainState>() {

    override val bridge: MainDomainLayerBridge<List<String>?, List<JokeBo>>? by inject(getDomainLayerBridgeId())
    private lateinit var _mainState: MutableLiveData<ScreenState<MainState>>
    override val screenState: LiveData<ScreenState<MainState>>
        get() {
            if (!::_mainState.isInitialized) {
                _mainState = MutableLiveData()
                requestJokes()
            }
            return _mainState
        }

    override fun getDomainLayerBridgeId(): String = MAIN_DOMAIN_BRIDGE_TAG

    private fun requestJokes() {
        _mainState.value = ScreenState.Loading
        bridge?.fetchJokes(scope = this, params = null,
            onResult = {
                _mainState.value = ScreenState.Render(MainState.Idle)
                it.either(::handleError, ::handleSuccess)
            })
    }

    private fun handleSuccess(list: List<JokeBo>) {
        _mainState.value = ScreenState.Render(MainState.ShowJokeList(list.boToVo()))
    }

    private fun handleError(failureBo: FailureBo) {
        _mainState.value = ScreenState.Render(MainState.ShowError(failureBo.boToVoFailure()))
    }

    fun onJokeItemClicked(item: JokeVo) {
        _mainState.value = ScreenState.Render(MainState.ShowJokeDetail(item))
    }

}