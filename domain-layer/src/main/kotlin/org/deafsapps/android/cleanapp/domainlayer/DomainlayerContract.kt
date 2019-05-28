package org.deafsapps.android.cleanapp.domainlayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo

interface DomainlayerContract {

    interface Presentationlayer {

        interface UseCase<in T, out S> {
            fun invoke(params: T?, onResult: (Either<FailureBo, S>) -> Unit) {
                val scope = CoroutineScope(Dispatchers.IO)
                // task undertaken in a worker thread
                val job = scope.async { run(params) }
                // once completed, result sent in the Main thread
                scope.launch(Dispatchers.Main) { onResult(job.await()) }
            }
            suspend fun run(params: T? = null): Either<FailureBo, S>
        }

    }

    interface Datalayer {

        interface FirebaseRepository<in T, out S> {
            fun loginUser(params: T): Either<FailureBo, S>
            fun registerUser(params: T): Either<FailureBo, S>
        }

        interface IcndbRepository<in T, out S> {
            suspend fun fetchJokes(params: T?): Either<FailureBo, S>
        }

    }

}