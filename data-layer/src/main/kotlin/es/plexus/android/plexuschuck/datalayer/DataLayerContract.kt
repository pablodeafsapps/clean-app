package es.plexus.android.plexuschuck.datalayer

import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import es.plexus.android.plexuschuck.domainlayer.base.Either
import retrofit2.Response

interface DataLayerContract {

    companion object {
        const val FIREBASE_DATA_SOURCE_TAG = "firebaseDataSource"
        const val ICNDB_DATA_SOURCE_TAG = "icndbDataSource"
    }

    interface FirebaseDataSource {
        fun requestFirebaseLogin(email: String, password: String): Either<FailureDto, Boolean>?
        fun requestFirebaseRegister(email: String, password: String): Either<FailureDto, Boolean>?
    }

    interface IcndbDataSource {
        suspend fun fetchIcndbJokesResponse(params: List<String>?): Response<JokeDtoWrapper>
    }

}