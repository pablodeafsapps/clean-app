package org.deafsapps.android.cleanapp.presentationlayer.feature.login.navigator

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseNavigator
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.view.ui.LoginActivity
import org.deafsapps.android.cleanapp.presentationlayer.feature.main.view.ui.MainActivity
import org.jetbrains.anko.startActivity

/**
 * This interface is the navigation from login feature
 */
interface LoginNavigator : BaseNavigator {

    /**
     * Navigate to Login activity and close the current
     */
    fun navigateToMainAndCloseThis()

}

/**
 * This class implements the navigation from login feature
 */
@ExperimentalCoroutinesApi
class LoginNavigatorImpl(private val loginActivity: LoginActivity) : LoginNavigator {

    override fun navigateToMainAndCloseThis() {
        loginActivity.startActivity<MainActivity>()
        loginActivity.finish()
    }

}
