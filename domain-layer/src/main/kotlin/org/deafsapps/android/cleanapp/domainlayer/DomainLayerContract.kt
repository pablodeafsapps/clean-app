package org.deafsapps.android.cleanapp.domainlayer

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.base.Either

interface DomainLayerContract {

    interface UseCase {

        fun <T> invoke(params: List<T?>? = null, onResult: (Either<Failure, Boolean>) -> Unit)
        suspend fun <T> run(params: List<T?>? = null): Either<Failure, Boolean>

    }

    interface Domain

    interface Repository {
        fun <T> loginUser(params: List<T?>? = null): Either<Failure, Boolean>
    }

}