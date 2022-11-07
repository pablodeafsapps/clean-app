package org.deafsapps.android.cleanapp.presentationlayer.feature.splash.navigator

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseNavigator
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.view.ui.LoginActivity
import org.deafsapps.android.cleanapp.presentationlayer.feature.splash.view.ui.SplashActivity
import org.jetbrains.anko.startActivity

/**
 * This interface is the navigation from splash feature
 */
interface SplashNavigator : BaseNavigator {

    /**
     * Navigate to Login activity and close the current
     */
    fun startLoginAndCloseThis()

}

/**
 * This class implements the navigation from splash feature
 */
@ExperimentalCoroutinesApi
class SplashNavigatorImpl(private val activity: SplashActivity) : SplashNavigator {

    override fun startLoginAndCloseThis() {
        activity.startActivity<LoginActivity>()
        activity.finish()
    }

}
