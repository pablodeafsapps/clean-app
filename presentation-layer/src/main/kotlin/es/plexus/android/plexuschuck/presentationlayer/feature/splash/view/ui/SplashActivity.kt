package es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import es.plexus.android.plexuschuck.domainlayer.feature.splash.SplashDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmView
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.ui.LoginActivity
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.view.state.SplashState
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.viewmodel.SplashActivityViewModel
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity(),
    BaseMvvmView<SplashActivityViewModel, SplashDomainLayerBridge, SplashState> {

    override val viewModel: SplashActivityViewModel? by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.onViewCreated()
    }

    override fun processRenderState(renderState: SplashState?) {
        when (renderState) {
            is SplashState.LoadingFinished -> startLoginActivity()
        }
    }

    private fun initModel() {
        viewModel?.screenState?.observe(this, Observer { screenState ->
            when (screenState) {
                is ScreenState.Render<SplashState> -> processRenderState(screenState.renderState)
            }
        })
    }

    private fun startLoginActivity() {
        startActivity<LoginActivity>()
        finish()
    }

}