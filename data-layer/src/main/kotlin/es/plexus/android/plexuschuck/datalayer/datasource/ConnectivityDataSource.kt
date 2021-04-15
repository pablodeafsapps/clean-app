package es.plexus.android.plexuschuck.datalayer.datasource

/**
 * This interface represents the contract to be complied by an entity to fit in as the connectivity
 * state provider
 */
interface ConnectivityDataSource {

    companion object {
        const val CONNECTIVITY_DATA_SOURCE_TAG = "connectivityDataSource"
    }

    /**
     * Returns the current state of the connection availability
     */
    suspend fun checkNetworkConnectionAvailability(): Boolean
}
