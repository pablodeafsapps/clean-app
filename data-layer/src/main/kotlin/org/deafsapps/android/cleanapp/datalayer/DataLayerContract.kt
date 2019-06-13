package org.deafsapps.android.cleanapp.datalayer

import org.deafsapps.android.cleanapp.datalayer.domain.JokeDtoWrapper
import retrofit2.Response

interface DataLayerContract {

    interface FirebaseDataSource {
        fun requestFirebaseLogin(email: String, password: String): Boolean?
        fun requestFirebaseRegister(email: String, password: String): Boolean?
    }

    interface IcndbDataSource {
        suspend fun fetchIcndbJokesResponse(params: List<String>?): Response<JokeDtoWrapper>
    }

}