package org.deafsapps.android.cleanapp.presentationlayer.main.presenter

import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.main.MainContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MainPresenter(private var view: MainContract.View?) : MainContract.Presenter, KoinComponent {

    private val mainDomainLayerBridge: MainDomainLayerBridge<List<String>?, List<JokeBo>>? by inject("mainDomainLayerBridge")

    override fun onAttach(mvpView: MainContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onViewResumed() {
        mainDomainLayerBridge?.fetchJokes(params = null,
            onResult = {
                it.either(::handleError, ::handleSuccess)
        })
    }

    override fun onJokeItemClicked(jokeItem: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDetach() {
        view = null
    }

    private fun handleSuccess(list: List<JokeBo>) {
        val a = list
    }

    private fun handleError(failureBo: FailureBo) {
        val b = failureBo
    }

}