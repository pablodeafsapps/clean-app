package es.plexus.android.plexuschuck.datalayer.datasource

import arrow.core.Either
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.UserSessionDto
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserSessionBo

/**
 * This interface represents the contract to be complied by an entity to fit in as the session data
 * system provider
 */
interface SessionDataSource {

    companion object {
        const val SESSION_DATA_SOURCE_TAG = "authenticationDataSource"

        const val SESSION_PREFERENCES = "sessionPreferences"
        const val SESSION_PREFERENCES_KEY_DATA = "sessionPreferencesKeyData"

        const val DEFAULT_VALUE = ""
    }

    /**
     * Save the session of an user
     * @param [userSession] the user data transfer object to save
     * @return An [Boolean] if successful or an [FailureBo] otherwise
     */
    suspend fun saveUserSession(userSession: UserSessionDto): Either<FailureDto, Boolean>

    /**
     * Get the current session of an user
     * @return An [UserSessionBo] if successful or an [FailureBo] otherwise
     */
    suspend fun getUserSession(): Either<FailureDto, UserSessionDto>

    /**
     * Remove the current session of an user
     * @return An [Boolean] if successful or an [FailureBo] otherwise
     */
    suspend fun removeUserSession(): Either<FailureDto, Boolean>
}
