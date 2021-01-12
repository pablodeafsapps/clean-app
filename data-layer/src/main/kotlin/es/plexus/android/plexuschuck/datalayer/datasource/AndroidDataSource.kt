package es.plexus.android.plexuschuck.datalayer.datasource

import android.content.Context
import android.content.SharedPreferences
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.UserSessionDto
import es.plexus.android.plexuschuck.datalayer.utils.isNetworkAvailable
import es.plexus.android.plexuschuck.datalayer.utils.isValid
import es.plexus.android.plexuschuck.domainlayer.domain.ErrorMessage
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * This class complies with [ConnectivityDataSource] and [SessionDataSource] so that it is
 * in charge of providing any required information regarding connectivity and user session
 */
class AndroidDataSource(private val context: Context) :
    ConnectivityDataSource,
    SessionDataSource {

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
