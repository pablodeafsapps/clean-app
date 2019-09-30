package es.plexus.android.plexuschuck.domainlayer

import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface DomainlayerContract {

    interface Presentationlayer {

        interface UseCase<in T, out S> {
            fun invoke(scope: CoroutineScope, params: T?, onResult: (Either<FailureBo, S>) -> Unit) {
                // task undertaken in a worker thread
                val job = scope.async { run(params) }
                // once completed, result sent in the Main thread
                scope.launch(Dispatchers.Main) { onResult(job.await()) }
            }

            suspend fun run(params: T? = null): Either<FailureBo, S>
        }

    }

    interface Datalayer {

        companion object {
            const val AUTHENTICATION_REPOSITORY_TAG = "authenticationRepository"
            const val JOKES_DATA_REPOSITORY_TAG = "jokesDataRepository"
        }

        interface FirebaseRepository<in T, out S> {
            fun loginUser(params: T): Either<FailureBo, S>
            fun registerUser(params: T): Either<FailureBo, S>
        }

        interface IcndbRepository<in T, out S> {
            suspend fun fetchJokes(params: T?): Either<FailureBo, S>
        }

    }

}