package org.deafsapps.android.cleanapp.domainlayer

import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo

interface DomainlayerContract {

    interface Presentationlayer {

        interface UseCase<in T> {
            fun invoke(params: List<T?>? = null, onResult: (Either<FailureBo, Boolean>) -> Unit)
            suspend fun run(params: List<T?>? = null): Either<FailureBo, Boolean>
        }

    }

    interface Datalayer {

        interface Repository<in T> {
            fun loginUser(params: List<T>): Either<FailureBo, Boolean>
            fun registerUser(params: List<T>): Either<FailureBo, Boolean>
        }

    }

}