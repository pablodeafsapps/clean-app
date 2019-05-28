package org.deafsapps.android.cleanapp.datalayer

import org.deafsapps.android.cleanapp.datalayer.domain.JokeDto

interface DataLayerContract {

    interface FirebaseDataSource {
        fun requestFirebaseLogin(email: String, password: String): Boolean?
        fun requestFirebaseRegister(email: String, password: String): Boolean?
    }

    interface IcndbDataSource {

        suspend fun fetchIcndbJokes(params: List<String>?): List<JokeDto>?
    }

}