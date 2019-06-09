package es.plexus.android.plexuschuck.presentationlayer.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

abstract class BaseMvvmFragment<T : BaseMvvmViewModel<S, U>, S : BaseDomainLayerBridge, U : BaseState>
    : Fragment(), BaseView<Fragment> {

    private lateinit var viewModelInstance: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModelInstance = ViewModelProviders.of(getViewInstance()).get(getViewModelClass())
    }

    protected fun getViewModelInstance(): T? = viewModelInstance

    abstract fun getViewModelClass(): Class<T>

}