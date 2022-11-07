package org.deafsapps.android.cleanapp.datalayer.datasource

import org.deafsapps.android.cleanapp.datalayer.db.UserWithFinancialProductEntity

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
