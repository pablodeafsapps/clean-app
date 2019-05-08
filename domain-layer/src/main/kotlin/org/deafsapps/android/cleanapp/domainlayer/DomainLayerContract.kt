package org.deafsapps.android.cleanapp.domainlayer

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.base.Either

interface DomainLayerContract {

    interface UseCase<T> {
        fun invoke(params: List<T?>? = null, onResult: (Either<Failure, Boolean>) -> Unit)
        suspend fun run(params: List<T?>? = null): Either<Failure, Boolean>
    }

    interface Domain

    interface Repository<T> {
        fun loginUser(params: List<T>): Either<Failure, Boolean>
        fun registerUser(params: List<T>): Either<Failure, Boolean>
    }

}