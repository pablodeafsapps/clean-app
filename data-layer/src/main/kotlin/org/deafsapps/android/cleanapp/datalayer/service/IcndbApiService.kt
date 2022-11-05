package org.deafsapps.android.cleanapp.datalayer.service

import org.deafsapps.android.cleanapp.datalayer.domain.JokeDtoWrapper
import retrofit2.Response
import retrofit2.http.POST

/**
 * This interface is used by Retrofit to conform the API service queries
 */
interface IcndbApiService {

    /**
     * This function returns a joke-related response from a HTTP POST query
     */
    @POST("jokes")
    suspend fun getJokesAsync(): Response<JokeDtoWrapper>

}
