package es.plexus.android.plexuschuck.presentationlayer.feature.detail.viewmodel

import es.plexus.android.plexuschuck.domainlayer.feature.detail.DetailDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.R
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.plexus.android.plexuschuck.presentationlayer.feature.detail.view.state.DetailState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DetailActivityViewModel(bridge: DetailDomainLayerBridge) :
    BaseMvvmViewModel<DetailDomainLayerBridge, DetailState>(bridge = bridge) {

    fun onViewCreated(jokeItem: JokeVo?) {
        _screenState.value = ScreenState.Render(
            if (jokeItem != null) DetailState.ShowJokeInfo(joke = jokeItem)
            else DetailState.ShowError(FailureVo.NoData(msgRes = R.string.error_no_data))
        )
    }

}