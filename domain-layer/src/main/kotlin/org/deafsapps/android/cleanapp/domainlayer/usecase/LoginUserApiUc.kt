package org.deafsapps.android.cleanapp.domainlayer.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

private const val REQUIRED_DATA = 2

class LoginUserApiUc : DomainlayerContract.Presentationlayer.UseCase<String?>, KoinComponent {

    private val repository: DomainlayerContract.Datalayer.Repository<String> by inject()

    override fun invoke(params: List<String?>?, onResult: (Either<FailureBo, Boolean>) -> Unit) {
        val scope = CoroutineScope(Dispatchers.IO)
        // task undertaken in a worker thread
        val job = scope.async { run(params) }
        // once completed, result sent in the Main thread
        scope.launch(Dispatchers.Main) { onResult(job.await()) }
    }

    override suspend fun run(params: List<String?>?): Either<FailureBo, Boolean> =
        params?.filterNotNull()?.let {
            if (it.size >= REQUIRED_DATA) {
                repository.loginUser(it)
            } else {
                Either.Left(FailureBo.Unknown)
            }
        } ?: run {
            Either.Left(FailureBo.Unknown)
        }

}