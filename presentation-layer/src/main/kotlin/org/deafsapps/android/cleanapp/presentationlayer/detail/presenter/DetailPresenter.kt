package org.deafsapps.android.cleanapp.presentationlayer.detail.presenter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.deafsapps.android.cleanapp.presentationlayer.detail.DetailContract
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import kotlin.coroutines.CoroutineContext

class DetailPresenter(private var view: DetailContract.View?) : DetailContract.Presenter {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onAttach(mvpView: DetailContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onViewResumed(item: JokeVo?) {
        view?.showLoading()

        if (item != null) {
            view?.loadJokeItem(item)
        }

        view?.hideLoading()
    }

    override fun onDetach() {
        view = null
        job.cancel()
    }

}