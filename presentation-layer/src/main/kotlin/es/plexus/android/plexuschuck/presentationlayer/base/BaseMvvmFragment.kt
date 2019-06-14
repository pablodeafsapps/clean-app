package es.plexus.android.plexuschuck.presentationlayer.base

import androidx.fragment.app.Fragment
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

abstract class BaseMvvmFragment<T : BaseMvvmViewModel<S, U>, S : BaseDomainLayerBridge, U : BaseState>
    : Fragment(), BaseView<Fragment> {

}