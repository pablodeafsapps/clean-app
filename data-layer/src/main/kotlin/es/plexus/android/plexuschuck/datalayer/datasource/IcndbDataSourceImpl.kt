package es.plexus.android.plexuschuck.datalayer.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import es.plexus.android.plexuschuck.datalayer.service.IcndbRetrofitApi
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