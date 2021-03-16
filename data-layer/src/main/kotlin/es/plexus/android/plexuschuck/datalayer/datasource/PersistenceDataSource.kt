package es.plexus.android.plexuschuck.datalayer.datasource

import es.plexus.android.plexuschuck.datalayer.db.UserWithFinancialProductEntity

interface PersistenceDataSource {

    companion object {
        const val PERSISTENCE_DATA_SOURCE_TAG = "persistenceDataSource"
    }

    /**
     * Returns the current state of the connection availability
     */
//    suspend fun saveUser(user: UserEntity)
    suspend fun saveUser()

    suspend fun getUser(): List<UserWithFinancialProductEntity>

}
