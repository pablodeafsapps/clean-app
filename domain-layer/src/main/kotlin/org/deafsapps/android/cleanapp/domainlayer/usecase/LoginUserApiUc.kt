package org.deafsapps.android.cleanapp.domainlayer.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LoginUserApiUc : DomainLayerContract.UseCase, KoinComponent {

    private val repository: DomainLayerContract.Repository by inject()

    override fun <T> invoke(params: List<T?>?, onResult: (Either<Failure, Boolean>) -> Unit) {

        val scope = CoroutineScope(Dispatchers.IO)
        // task undertaken in a worker thread
        val job = scope.async { run(params) }
        // once completed, result sent in the Main thread
        scope.launch(Dispatchers.Main) { onResult(job.await()) }

    }

    override suspend fun <T> run(params: List<T?>?): Either<Failure, Boolean> = repository.loginUser(params)

}