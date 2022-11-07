package org.deafsapps.android.cleanapp.datalayer.db

import androidx.room.*

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEntity(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinancialProductEntity(financialProductEntity: FinancialProductEntity)

    @Update
    suspend fun updateUserEntity(userEntity: UserEntity)

    @Update
    suspend fun updateFinancialProductEntity(financialProductEntity: FinancialProductEntity)

    @Delete
    suspend fun deleteUserEntity(userEntity: UserEntity)

    @Delete
    suspend fun deleteFinancialProductEntity(financialProductEntity: FinancialProductEntity)

    @Transaction
    @Query("SELECT * FROM user_entity")
    suspend fun getUsersAndProducts(): List<UserWithFinancialProductEntity>

//    @Transaction
//    @Query("SELECT * FROM financial_product_entity WHERE ownerId LIKE :userEntity")
//    suspend fun getProductsByUser(userEntity: UserEntity)

}
