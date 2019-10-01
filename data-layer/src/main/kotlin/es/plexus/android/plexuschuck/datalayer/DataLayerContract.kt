package es.plexus.android.plexuschuck.datalayer

import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import es.plexus.android.plexuschuck.domainlayer.base.Either
import retrofit2.Response

interface DataLayerContract {

    companion object {
        const val AUTHENTICATION_DATA_SOURCE_TAG = "authenticationDataSource"
        const val JOKES_DATA_SOURCE_TAG = "jokesDataSource"
    }

    interface AuthenticationDataSource {
        fun requestLogin(email: String, password: String): Either<FailureDto, Boolean>?
        fun requestRegister(email: String, password: String): Either<FailureDto, Boolean>?
    }

    interface JokesDataSource {
        suspend fun fetchJokesResponse(params: List<String>?): Response<JokeDtoWrapper>
    }

}