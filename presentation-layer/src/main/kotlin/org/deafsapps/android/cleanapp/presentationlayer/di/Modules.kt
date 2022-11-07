package org.deafsapps.android.cleanapp.presentationlayer.di

import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LOGIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.cleanapp.presentationlayer.feature.detail.viewmodel.DetailViewModel
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.viewmodel.LoginViewModel
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.viewmodel.MainViewModel
import org.deafsapps.android.cleanapp.presentationlayer.feature.splash.viewmodel.SplashActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * This variable represents the 'presentation-layer' dependencies module to be used by Koin. It
 * basically includes ViewModel definitions.
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
@ExperimentalCoroutinesApi
val presentationLayerModule = module(override = true) {

    scope<SplashActivity> {
        scoped<SplashNavigator> { SplashNavigatorImpl(get()) }
        viewModel {
            SplashActivityViewModel(bridge = BaseDomainLayerBridge.None, navigator = get())
        }
    }

    scope<LoginActivity> {
        scoped<LoginNavigator> { LoginNavigatorImpl(get()) }
        viewModel {
            LoginViewModel(bridge = get(named(name = LOGIN_DOMAIN_BRIDGE_TAG)), navigator = get())
        }
    }

    scope<MainActivity> {
        scoped<MainNavigator<JokeVo>> { MainNavigatorImpl(get()) }
        viewModel {
            MainViewModel(bridge = get(named(name = MAIN_DOMAIN_BRIDGE_TAG)), navigator = get())
        }
    }

    scope<DetailActivity> {
        scoped<DetailNavigator> { DetailNavigatorImpl() }
        viewModel {
            DetailViewModel(bridge = BaseDomainLayerBridge.None, get())
        }
    }
}
