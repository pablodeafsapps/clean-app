package org.deafsapps.android.cleanapp.datalayer.service

import kotlinx.coroutines.Deferred
import org.deafsapps.android.cleanapp.datalayer.domain.JokeDtoWrapper
import retrofit2.Response
import retrofit2.http.PUT

interface IcndbRetrofitApi {

    @PUT("jokes")
    fun getJokesAsync(): Deferred<Response<JokeDtoWrapper>>

}