package org.deafsapps.android.cleanapp.datalayer.datasource

import android.content.Context
import android.content.SharedPreferences
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.deafsapps.android.cleanapp.datalayer.domain.FailureDto
import org.deafsapps.android.cleanapp.datalayer.domain.UserSessionDto
import org.deafsapps.android.cleanapp.datalayer.utils.isNetworkAvailable
import org.deafsapps.android.cleanapp.datalayer.utils.isValid
import org.deafsapps.android.cleanapp.domainlayer.domain.ErrorMessage
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.deafsapps.android.cleanapp.datalayer.db.AppDatabase
import org.deafsapps.android.cleanapp.datalayer.db.FinancialProductEntity
import org.deafsapps.android.cleanapp.datalayer.db.UserEntity
import org.deafsapps.android.cleanapp.datalayer.db.UserWithFinancialProductEntity

/**
 * This class complies with [ConnectivityDataSource], [SessionDataSource], and [PersistenceDataSource]
 * so that it is in charge of providing any required information regarding connectivity, user session,
 * and data persistency.
 */
class AndroidDataSource(
        private val context: Context,
        private val database: AppDatabase
) : ConnectivityDataSource, SessionDataSource, PersistenceDataSource {

    override suspend fun checkNetworkConnectionAvailability(): Boolean =
        context.isNetworkAvailable()

    override suspend fun saveUserSession(userSession: UserSessionDto): Either<FailureDto, Boolean> =
        context.getSharedPreferences(SessionDataSource.SESSION_PREFERENCES, Context.MODE_PRIVATE)
            ?.let { sessionData ->
                sessionData.edit()?.let { editor ->
                    editor.putString(
                        SessionDataSource.SESSION_PREFERENCES_KEY_DATA,
                        Json.encodeToString(userSession)
                    )

                    if (editor.commit()) {
                        if (sessionData.toUserSessionDto() == userSession) {
                            true.right()
                        } else {
                            FailureDto.Error(ErrorMessage.ERROR_GET_SESSION_SHARED_PREFERENCES_CORRUPTED)
                                .left()
                        }
                    } else {
                        FailureDto.Error(ErrorMessage.ERROR_GET_SESSION_SHARED_PREFERENCES_COMMIT_ERROR)
                            .left()
                    }

                } ?: run {
                    FailureDto.Error(ErrorMessage.ERROR_GET_SESSION_SHARED_PREFERENCES_EDITABLE)
                        .left()
                }
            } ?: run {
            FailureDto.Error(ErrorMessage.ERROR_GET_SESSION_SHARED_PREFERENCES).left()
        }

    override suspend fun getUserSession(): Either<FailureDto, UserSessionDto> =
        context.getSharedPreferences(SessionDataSource.SESSION_PREFERENCES, Context.MODE_PRIVATE)
            ?.let { sessionData ->
                sessionData.toUserSessionDto()?.let { userSessionDto ->
                    if (userSessionDto.isValid()) {
                        userSessionDto.right()
                    } else {
                        FailureDto.NoData.left()
                    }
                } ?: run {
                    FailureDto.NoData.left()
                }
            } ?: run {
            FailureDto.NoData.left()
        }

    override suspend fun removeUserSession(): Either<FailureDto, Boolean> =
        context
            .getSharedPreferences(SessionDataSource.SESSION_PREFERENCES, Context.MODE_PRIVATE)
            ?.let { sessionData ->
                sessionData.edit()?.let { editor ->
                    editor.remove(SessionDataSource.SESSION_PREFERENCES_KEY_DATA)

                    if (editor.commit()) {
                        if (sessionData.toUserSessionDto() == null) {
                            true.right()
                        } else {
                            FailureDto.Error(ErrorMessage.ERROR_GET_SESSION_SHARED_PREFERENCES_CORRUPTED)
                                .left()
                        }
                    } else {
                        FailureDto.Error(ErrorMessage.ERROR_GET_SESSION_SHARED_PREFERENCES_COMMIT_ERROR)
                            .left()
                    }
                } ?: run {
                    FailureDto.Unknown.left()
                }
            } ?: run {
            FailureDto.Unknown.left()
        }

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

    private fun SharedPreferences.toUserSessionDto(): UserSessionDto? =
        try {
            Json.decodeFromString<UserSessionDto>(
                getString(
                    SessionDataSource.SESSION_PREFERENCES_KEY_DATA,
                    SessionDataSource.DEFAULT_VALUE
                ) ?: SessionDataSource.DEFAULT_VALUE
            )
        } catch (e: SerializationException) {
            null
        }

}
