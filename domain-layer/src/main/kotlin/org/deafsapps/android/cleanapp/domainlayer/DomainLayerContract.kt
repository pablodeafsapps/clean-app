package org.deafsapps.android.cleanapp.domainlayer

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.base.Either

interface DomainLayerContract {

    interface UseCase {

        suspend fun enqueue(): Either<Failure, Boolean>

    }

    interface Domain

    interface Repository {
        fun loginUser(): Either<Failure, Boolean>
    }

}