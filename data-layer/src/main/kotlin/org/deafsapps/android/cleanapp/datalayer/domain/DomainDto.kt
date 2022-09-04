package org.deafsapps.android.cleanapp.datalayer.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.deafsapps.android.cleanapp.domainlayer.domain.ErrorMessage
import kotlinx.serialization.Serializable
import okhttp3.ResponseBody

/**
 * This data class represents the Data Transfer Object related to a user login datum
 */
data class UserLoginDto(val email: String, val password: String)

/**
 * This data class represents the Data Transfer Object related to a user session datum
 */
@Serializable
data class UserSessionDto(val uuid: String, val email: String, val name: String) {
    override fun equals(other: Any?): Boolean {
        return if (other is UserSessionDto) {
            uuid == other.uuid && email == other.email && name == other.name
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

/**
 * This data class models a wrapper over a [JokeDto] datum
 */
data class JokeDtoWrapper(val type: String, val value: List<JokeDto>)

/**
 * This data class represents the Data Transfer Object related to a joke datum
 */
@JsonClass(generateAdapter = true)
data class JokeDto(
    @Json(name = "id") val id: Int?,
    @Json(name = "joke") val joke: String?,
    @Json(name = "categories") val categories: List<String>?
)

/**
 * A class which models any failure coming from the 'data-layer'
 */
sealed class FailureDto(val msg: String?) {

    companion object {
        private const val DEFAULT_ERROR_CODE = -1
    }

    object NoConnection : FailureDto(msg = ErrorMessage.ERROR_NO_CONNECTION)
    class RequestError(val code: Int = DEFAULT_ERROR_CODE, msg: String?, val errorBody: ResponseBody? = null) : FailureDto(msg = msg)
    object FirebaseLoginError : FailureDto(msg = ErrorMessage.ERROR_LOGIN_REQUEST)
    object FirebaseLoginCorruptError : FailureDto(msg = ErrorMessage.ERROR_LOGIN_CORRUPT_REQUEST)
    class FirebaseRegisterError(msg: String?) : FailureDto(msg = msg)
    object NoData : FailureDto(msg = ErrorMessage.ERROR_NO_DATA)
    object Unknown : FailureDto(msg = ErrorMessage.ERROR_UNKNOWN)
    class Error(msg: String?) : FailureDto(msg = msg)
}
