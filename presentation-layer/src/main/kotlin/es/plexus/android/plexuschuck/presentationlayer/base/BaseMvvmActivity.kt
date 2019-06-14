package es.plexus.android.plexuschuck.presentationlayer.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

abstract class BaseMvvmActivity<T : BaseMvvmViewModel<S, U>, S : BaseDomainLayerBridge, U : BaseState>
    : AppCompatActivity(), BaseView<FragmentActivity> {

    abstract fun processRenderState(renderState: U?): Unit

}