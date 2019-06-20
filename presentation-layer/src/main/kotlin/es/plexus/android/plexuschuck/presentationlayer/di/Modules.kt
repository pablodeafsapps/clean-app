package es.plexus.android.plexuschuck.presentationlayer.di

import es.plexus.android.plexuschuck.presentationlayer.feature.detail.viewmodel.DetailActivityViewModel
import es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel.LoginActivityViewModel
import es.plexus.android.plexuschuck.presentationlayer.feature.main.viewmodel.MainActivityViewModel
import es.plexus.android.plexuschuck.presentationlayer.feature.splash.viewmodel.SplashActivityViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationLayerModule = module(override = true) {
    viewModel { SplashActivityViewModel() }
    viewModel { LoginActivityViewModel() }
    viewModel { MainActivityViewModel() }
    viewModel { DetailActivityViewModel() }
}