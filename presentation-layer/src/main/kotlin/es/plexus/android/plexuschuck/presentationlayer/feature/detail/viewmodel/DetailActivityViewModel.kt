package es.plexus.android.plexuschuck.presentationlayer.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.plexus.android.plexuschuck.domainlayer.feature.detail.DETAIL_DOMAIN_BRIDGE_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.detail.DetailDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmViewModel
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.JokeVo
import es.plexus.android.plexuschuck.presentationlayer.feature.detail.view.state.DetailState

class DetailActivityViewModel : BaseMvvmViewModel<DetailDomainLayerBridge, DetailState>() {

    override val bridge: DetailDomainLayerBridge? = null   // no 'domain-layer' needed
    private lateinit var _detailState: MutableLiveData<ScreenState<DetailState>>
    override val screenState: LiveData<ScreenState<DetailState>>
        get() {
            if (!::_detailState.isInitialized) {
                _detailState = MutableLiveData()
            }
            return _detailState
        }

    override fun getDomainLayerBridgeId(): String = DETAIL_DOMAIN_BRIDGE_TAG

    fun onViewCreated(jokeItem: JokeVo?) {
        _detailState.value = ScreenState.Render(
            if (jokeItem != null) DetailState.ShowJokeInfo(jokeItem) else DetailState.ShowError(FailureVo.NoDataError("No data"))
        )
    }

}