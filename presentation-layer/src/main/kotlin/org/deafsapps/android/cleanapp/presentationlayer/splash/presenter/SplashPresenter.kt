package org.deafsapps.android.cleanapp.presentationlayer.splash.presenter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.deafsapps.android.cleanapp.presentationlayer.splash.SplashContract
import kotlin.coroutines.CoroutineContext

class SplashPresenter(private var view: SplashContract.View?) : SplashContract.Presenter {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onAttach(mvpView: SplashContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onViewCreated() {
        view?.finishView()
    }

    override fun onDetach() {
        view = null
        job.cancel()
    }

}