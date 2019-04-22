package org.deafsapps.android.cleanapp.presentationlayer.splash.view.ui

import androidx.appcompat.app.AppCompatActivity
import org.deafsapps.android.cleanapp.presentationlayer.login.view.ui.LoginActivity
import org.deafsapps.android.cleanapp.presentationlayer.splash.SplashContract
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val splashPresenter: SplashContract.Presenter by inject { parametersOf(this) }

    override fun onResume() {
        super.onResume()

        startActivity<LoginActivity>()
        splashPresenter.onViewCreated()
    }

    override fun finishView() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        splashPresenter.onDetach()
    }

}