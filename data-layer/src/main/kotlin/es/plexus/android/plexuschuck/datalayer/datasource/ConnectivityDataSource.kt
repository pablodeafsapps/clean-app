package es.plexus.android.plexuschuck.datalayer.datasource

import android.content.Context
import es.plexus.android.plexuschuck.datalayer.utils.isNetworkAvailable

/**
 *
 */
interface ConnectivityDataSource {

    companion object {
        const val CONNECTIVITY_DATA_SOURCE_TAG = "connectivityDataSource"
    }

    /**
     *
     */
    suspend fun checkNetworkConnectionAvailability(): Boolean

}

/**
 *
 */
class AndroidDataSource(private val context: Context) : ConnectivityDataSource {

    override suspend fun checkNetworkConnectionAvailability(): Boolean =
        context.isNetworkAvailable()

}
