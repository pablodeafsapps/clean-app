package es.plexus.android.plexuschuck.presentationlayer.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

/**
 * This parametrized abstract class is intended to be extended by any app presentation-layer view-model which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param S represents the state of the view, and must extend from [BaseState]
 * @property screenState the [StateFlow] which will be updated to notify the view
 * @property viewModelJob represents the job to be launched for the coroutine
 * @property coroutineContext a context to host the coroutine
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
@ExperimentalCoroutinesApi
abstract class BaseMvvmViewModel<T : BaseDomainLayerBridge, S : BaseState>(val bridge: T) : ViewModel(),
    CoroutineScope {

    protected lateinit var _screenState: MutableStateFlow<ScreenState<S>>
    val screenState: StateFlow<ScreenState<S>>
        get() {
            if (!::_screenState.isInitialized) {
                _screenState = MutableStateFlow(ScreenState.Idle)
            }
            return _screenState
        }

    private val viewModelJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + viewModelJob

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}