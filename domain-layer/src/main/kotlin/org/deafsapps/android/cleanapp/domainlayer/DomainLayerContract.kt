package org.deafsapps.android.cleanapp.domainlayer

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.base.Either

interface DomainLayerContract {

    interface UseCase {

        fun <Params> invoke(params: Params? = null, onResult: (Either<Failure, Boolean>) -> Unit)
        suspend fun <Params> run(params: Params): Either<Failure, Boolean>

    }

    interface Domain

    interface Repository {
        fun <Params> loginUser(params: Params? = null): Either<Failure, Boolean>
    }

}