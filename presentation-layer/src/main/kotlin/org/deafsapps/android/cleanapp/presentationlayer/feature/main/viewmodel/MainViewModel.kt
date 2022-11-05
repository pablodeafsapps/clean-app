package org.deafsapps.android.cleanapp.presentationlayer.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.boToVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.state.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This [BaseMvvmViewModel] handles the 'main' feature view-model. Therefore, it is in charge of
 * the logic which allows a user to fetch joke data and access a joke detail information. It uses a
 * [MainDomainLayerBridge] which gathers all the operations available for this entity.
 *
 * All results update an observable variable, [_screenState], with [MainState] values.
 */
@ExperimentalCoroutinesApi
class MainViewModel(bridge: MainDomainLayerBridge<JokeBoWrapper>) :
    BaseMvvmViewModel<MainDomainLayerBridge<JokeBoWrapper>, MainState>(bridge = bridge) {

    /**
     * Indicates that the associated view has been created
     */
    fun onViewCreated() {
        _screenState.value = ScreenState.Loading
        bridge.fetchJokes(
            scope = viewModelScope,
            onResult = {
                it.fold(::handleError, ::handleSuccess)
            }
        )
    }

    /**
     * Indicates that a joke item of the associated view has been selected
     */
    fun onJokeItemSelected(item: JokeVo) {
        _screenState.value = ScreenState.Render(MainState.ShowJokeDetail(joke = item))
    }

    /**
     * Method fot end the current session of the user
     */
    fun onLogoutSelected() {
        bridge.logout(
            scope = viewModelScope,
            onResult = {
                it.fold(::handleError) {
                    _screenState.value = ScreenState.Render(MainState.QuitSession)
                }
            }
        )
    }

    private fun handleSuccess(wrapper: JokeBoWrapper) {
        _screenState.value = ScreenState.Render(MainState.ShowJokeList(jokeList = wrapper.value.boToVo()))
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value = ScreenState.Render(MainState.ShowError(failure = failureBo.boToVoFailure()))
    }

}
