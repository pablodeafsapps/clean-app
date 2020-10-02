package es.plexus.android.plexuschuck.presentationlayer.feature.main.viewmodel

import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBoWrapper
import es.plexus.android.plexuschuck.domainlayer.feature.main.MainDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.plexus.android.plexuschuck.presentationlayer.domain.boToVo
import es.plexus.android.plexuschuck.presentationlayer.domain.boToVoFailure
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.state.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivityViewModel(bridge: MainDomainLayerBridge<JokeBoWrapper>) :
    BaseMvvmViewModel<MainDomainLayerBridge<JokeBoWrapper>, MainState>(bridge = bridge) {

    fun onViewCreated() {
        _screenState.value = ScreenState.Loading
        bridge.fetchJokes(scope = this, onResult = {
            it.fold(::handleError, ::handleSuccess)
        })
    }

    fun onJokeItemSelected(item: JokeVo) {
        _screenState.value = ScreenState.Render(MainState.ShowJokeDetail(item))
    }

    private fun handleSuccess(wrapper: JokeBoWrapper) {
        _screenState.value = ScreenState.Render(MainState.ShowJokeList(wrapper.value.boToVo()))
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value = ScreenState.Render(MainState.ShowError(failureBo.boToVoFailure()))
    }

}