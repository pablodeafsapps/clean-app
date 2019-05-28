package org.deafsapps.android.cleanapp.datalayer.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.domain.JokeDto
import org.deafsapps.android.cleanapp.datalayer.service.IcndbRetrofitApi
import org.koin.standalone.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IcndbDataSourceImpl : DataLayerContract.IcndbDataSource, KoinComponent {

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl("http://api.icndb.com")

    override suspend fun fetchIcndbJokes(params: List<String>?): List<JokeDto>? =
        retrofitBuilder.build().create(IcndbRetrofitApi::class.java).getJokes().await().value

}