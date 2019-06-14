package es.plexus.android.plexuschuck.presentationlayer.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext

abstract class BaseMvvmViewModel<T : BaseDomainLayerBridge, S : BaseState> : ViewModel(), CoroutineScope, KoinComponent {

    abstract val screenState: LiveData<ScreenState<S>>

    private val bridge: BaseDomainLayerBridge? by inject(getDomainLayerBridgeId())

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    private val viewModelJob = SupervisorJob()

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    protected fun getDomainLayerBridge(): BaseDomainLayerBridge? = bridge

    /**
     * To be implemented by the ViewModel implementation
     */
    abstract fun getDomainLayerBridgeId(): String

}