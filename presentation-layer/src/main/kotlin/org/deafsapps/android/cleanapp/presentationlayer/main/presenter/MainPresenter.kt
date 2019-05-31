package org.deafsapps.android.cleanapp.presentationlayer.main.presenter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.domain.JokeVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.boToVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.cleanapp.presentationlayer.main.MainContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext

class MainPresenter(private var view: MainContract.View?) : MainContract.Presenter, KoinComponent {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val mainDomainLayerBridge: MainDomainLayerBridge<List<String>?, List<JokeBo>>? by inject("mainDomainLayerBridge")

    override fun onAttach(mvpView: MainContract.View) {
        //No need to define it since 'view' is already injected through constructor
    }

    override fun onViewResumed() {
        mainDomainLayerBridge?.fetchJokes(scope = this, params = null,
            onResult = {
                it.either(::handleError, ::handleSuccess)
            })
    }

    override fun onJokeItemClicked(item: JokeVo) {
        view?.navigateToDetailActivity(item)
    }

    override fun onDetach() {
        view = null
        job.cancel()
    }

    private fun handleSuccess(list: List<JokeBo>) {
        view?.loadJokesData(list.boToVo())
    }

    private fun handleError(failureBo: FailureBo) {
        view?.showInfoMessage(failureBo.boToVoFailure().getErrorMessage())
    }

}