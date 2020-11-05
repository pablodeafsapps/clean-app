package es.plexus.android.plexuschuck.datalayer.datasource

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import arrow.core.Either
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.dtoToBo
import es.plexus.android.plexuschuck.datalayer.service.IcndbApiService
import es.plexus.android.plexuschuck.datalayer.utils.isNetworkAvailable
import es.plexus.android.plexuschuck.datalayer.utils.safeCall
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBoWrapper
import retrofit2.Retrofit

interface ConnectivityDataSource {

    companion object {
        const val CONNECTIVITY_DATA_SOURCE_TAG = "connectivityDataSource"
    }

    suspend fun checkNetworkConnectionAvailability(): Boolean

}

class AndroidDataSource(private val context: Context) : ConnectivityDataSource {

    override suspend fun checkNetworkConnectionAvailability(): Boolean =
        context.isNetworkAvailable()

}