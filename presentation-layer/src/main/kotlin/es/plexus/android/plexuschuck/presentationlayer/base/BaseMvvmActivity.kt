package es.plexus.android.plexuschuck.presentationlayer.base

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

abstract class BaseMvvmActivity<T : BaseMvvmViewModel<S, U>, S : BaseDomainLayerBridge, U : BaseState>
    : AppCompatActivity(), BaseView<FragmentActivity> {

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

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

}