package org.deafsapps.android.cleanapp.datalayer.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.domain.JokeDtoWrapper
import org.deafsapps.android.cleanapp.datalayer.service.IcndbRetrofitApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ICNDB_BASE_URL = "http://api.icndb.com"

class IcndbDataSourceImpl : DataLayerContract.IcndbDataSource {

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(ICNDB_BASE_URL)

    override suspend fun fetchIcndbJokesResponse(params: List<String>?): Response<JokeDtoWrapper> =
        retrofitBuilder.build().create(IcndbRetrofitApi::class.java).getJokesAsync().await()

}