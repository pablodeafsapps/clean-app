package es.plexus.android.plexuschuck.datalayer.datasource

import android.content.Context
import es.plexus.android.plexuschuck.datalayer.db.AppDatabase
import es.plexus.android.plexuschuck.datalayer.db.FinancialProductEntity
import es.plexus.android.plexuschuck.datalayer.db.UserEntity
import es.plexus.android.plexuschuck.datalayer.db.UserWithFinancialProductEntity
import es.plexus.android.plexuschuck.datalayer.utils.isNetworkAvailable

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

/**
 * This class complies with [ConnectivityDataSource] so that it is in charge of providing any
 * required information regarding connectivity
 */
class AndroidDataSource(
    private val context: Context,
    private val database: AppDatabase
) : ConnectivityDataSource, PersistenceDataSource {

    override suspend fun checkNetworkConnectionAvailability(): Boolean =
        context.isNetworkAvailable()

//    override suspend fun saveUser(user: UserEntity) {
    override suspend fun saveUser() {
        database.appDao().insertUserEntity(userEntity = UserEntity())
        database.appDao().insertFinancialProductEntity(financialProductEntity = FinancialProductEntity(
            ownerId = 1, pName = "product 0", pId = 0, pOrder = 0
        ))
        database.appDao().insertFinancialProductEntity(financialProductEntity = FinancialProductEntity(
            ownerId = 1, pName = "product 1", pId = 1, pOrder = 1
        ))
        database.appDao().insertFinancialProductEntity(financialProductEntity = FinancialProductEntity(
            ownerId = 1, pName = "product 2", pId = 2, pOrder = 2
        ))
    }

    override suspend fun getUser(): List<UserWithFinancialProductEntity> =
        database.appDao().getUsersAndProducts()

}
