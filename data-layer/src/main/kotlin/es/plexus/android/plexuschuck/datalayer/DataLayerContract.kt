package es.plexus.android.plexuschuck.datalayer

import es.plexus.android.plexuschuck.datalayer.base.FailureDto
import es.plexus.android.plexuschuck.domainlayer.base.Either

interface DataLayerContract {

    interface FirebaseDataSource {
        fun requestFirebaseLogin(email: String, password: String): Either<FailureDto, Boolean>?
        fun requestFirebaseRegister(email: String, password: String): Either<FailureDto, Boolean>?
    }

}