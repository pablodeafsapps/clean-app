package es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import es.plexus.android.plexuschuck.domainlayer.feature.splash.SplashDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmActivity
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.ui.LoginActivity
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.state.SplashState
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.viewmodel.SplashActivityViewModel
import org.jetbrains.anko.startActivity

class SplashActivity : BaseMvvmActivity<SplashActivityViewModel, SplashDomainLayerBridge, SplashState>() {

    private val splashViewModel: SplashActivityViewModel? by lazy { getViewModelInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initModel()
    }

    override fun onResume() {
        super.onResume()
        splashViewModel?.onViewCreated()
    }

    override fun getViewModelClass(): Class<SplashActivityViewModel> = SplashActivityViewModel::class.java

    override fun getViewInstance(): FragmentActivity = this

    private fun initModel() {
        splashViewModel?.screenState?.observe(this, Observer { screenState ->
            when (screenState) {
                is ScreenState.Render<SplashState> -> processRenderState(screenState.renderState)
            }
        })
    }

    private fun processRenderState(renderState: SplashState?) {
        when (renderState) {
            is SplashState.FinishView -> startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        startActivity<LoginActivity>()
    }

}