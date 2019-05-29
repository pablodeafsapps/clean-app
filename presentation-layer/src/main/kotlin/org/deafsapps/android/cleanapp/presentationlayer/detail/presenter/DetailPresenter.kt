package org.deafsapps.android.cleanapp.presentationlayer.detail.presenter

import org.deafsapps.android.cleanapp.presentationlayer.detail.DetailContract
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo

class DetailPresenter(private var view: DetailContract.View?) : DetailContract.Presenter {

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
    }

}