package es.plexus.android.plexuschuck.presentationlayer.di

import es.plexus.android.plexuschuck.presentationlayer.feature.detail.viewmodel.DetailActivityViewModel
import es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel.LoginActivityViewModel
import es.plexus.android.plexuschuck.presentationlayer.feature.main.viewmodel.MainActivityViewModel
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.viewmodel.SplashActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * This variable represents the 'presentation-layer' dependencies module to be used by Koin. It basically includes
 * ViewModel definitions.
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
val presentationLayerModule = module(override = true) {
    viewModel { SplashActivityViewModel() }
    viewModel { LoginActivityViewModel() }
    viewModel { MainActivityViewModel() }
    viewModel { DetailActivityViewModel() }
}