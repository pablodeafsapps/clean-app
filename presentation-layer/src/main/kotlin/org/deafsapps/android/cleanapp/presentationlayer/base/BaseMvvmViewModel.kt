package org.deafsapps.android.cleanapp.presentationlayer.base

import androidx.lifecycle.ViewModel
import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This parametrized abstract class is intended to be extended by any app presentation-layer view-model which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param T represents the bridge of the view, and must extend from [BaseDomainLayerBridge]
 * @param N represents the navigation of the view, and must extend from [BaseNavigator]
 * @param S represents the state of the view, and must extend from [BaseState]
 * @property screenState the [StateFlow] which will be updated to notify the view
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
@ExperimentalCoroutinesApi
abstract class BaseMvvmViewModel<T : BaseDomainLayerBridge, N : BaseNavigator, S : BaseState>(
    protected val bridge: T,
    protected val navigator: N
) : ViewModel() {

    protected val _screenState: MutableStateFlow<ScreenState<S>> by lazy {
        MutableStateFlow(
            ScreenState.Idle
        )
    }
    val screenState: StateFlow<ScreenState<S>>
        get() {
            return _screenState
        }

}
