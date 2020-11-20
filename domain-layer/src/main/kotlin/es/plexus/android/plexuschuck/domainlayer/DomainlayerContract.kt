package es.plexus.android.plexuschuck.domainlayer

import arrow.core.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 */
interface DomainlayerContract {

    /**
     *
     */
    interface Presentationlayer {

        /**
         *
         */
        interface UseCase<in T, out S> {
            /**
             *
             */
            fun invoke(
                scope: CoroutineScope,
                params: T? = null,
                onResult: (Either<FailureBo, S>) -> Unit,
                dispatcherWorker: CoroutineDispatcher = Dispatchers.IO
            ) {
                /**
                 * Task undertaken in a dispatcher worker and once completed, result sent in the scope thread
                 */
                scope.launch { onResult(withContext(dispatcherWorker) { run(params) }) }
            }

            /**
             *
             */
            suspend fun run(params: T? = null): Either<FailureBo, S>
        }

    }

    /**
     *
     */
    interface Datalayer {

        companion object {
            const val AUTHENTICATION_REPOSITORY_TAG = "authenticationRepository"
            const val DATA_REPOSITORY_TAG = "dataRepository"
        }

        /**
         *
         */
        interface AuthenticationRepository<in T, out S> {
            /**
             *
             */
            suspend fun loginUser(params: T): Either<FailureBo, S>
            /**
             *
             */
            suspend fun registerUser(params: T): Either<FailureBo, S>
        }

        /**
         *
         */
        interface DataRepository<out T> {
            /**
             *
             */
            suspend fun fetchJokes(): Either<FailureBo, T>
        }

    }

}
