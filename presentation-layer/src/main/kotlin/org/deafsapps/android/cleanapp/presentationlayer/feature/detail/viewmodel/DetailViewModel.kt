package org.deafsapps.android.cleanapp.presentationlayer.feature.detail.viewmodel

import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.feature.detail.view.state.DetailState
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This [BaseMvvmViewModel] handles the 'detail' feature view-model. Therefore, it is in charge of
 * publishing joke detail information to all subscribers.
 *
 * All results update an observable variable, [_screenState], with [DetailState] values.
 */
@ExperimentalCoroutinesApi
class DetailViewModel(
    bridge: BaseDomainLayerBridge.None, navigator: DetailNavigator
) : BaseMvvmViewModel<BaseDomainLayerBridge.None, DetailNavigator, DetailState>(
    bridge = bridge, navigator = navigator
) {

    /**
     * Indicates that the associated view has been created
     */
    fun onViewCreated(jokeItem: JokeVo?) {
        _screenState.value = ScreenState.Render(
            if (jokeItem != null) DetailState.ShowJokeInfo(joke = jokeItem)
            else DetailState.ShowError(FailureVo.NoData)
        )
    }

}
