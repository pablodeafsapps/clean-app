package org.deafsapps.android.cleanapp.presentationlayer.feature.splash.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.feature.splash.navigator.SplashNavigator
import org.deafsapps.android.cleanapp.presentationlayer.feature.splash.view.state.SplashState
import org.deafsapps.android.cleanapp.presentationlayer.feature.splash.viewmodel.SplashActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.scope.ScopeActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * This [AppCompatActivity] represents the typical splash screen used to load the application
 */
@ExperimentalCoroutinesApi
class SplashActivity : ScopeActivity(),
    BaseMvvmView<SplashActivityViewModel, BaseDomainLayerBridge.None, SplashNavigator, SplashState> {

    override val viewModel: SplashActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewCreated()
    }

    override fun processRenderState(renderState: SplashState) {
        when (renderState) {
            is SplashState.LoadingFinished -> viewModel.startLoginActivityAndCloseThis()
        }
    }

    override fun initModel() {
        lifecycleScope.launch {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Render<SplashState> -> processRenderState(screenState.renderState)
                    else -> {}
                }
            }
        }
    }

}
