package org.deafsapps.android.cleanapp.domainlayer.domain

private const val DEFAULT_STRING_RESOURCE = "none"

/**
 * This data class represents the Business Object related to a user login datum
 */
data class UserLoginBo(val email: String, val password: String)

/**
 * This data class represents the Business Object related to a user with session
 */
data class UserSessionBo(val uuid: String, val email: String, val name: String, val lastAccess: Long)

/**
 * This data class models a wrapper over a [JokeBo] datum
 */
data class JokeBoWrapper(val type: String, val value: List<JokeBo>)

/**
 * This data class represents the Business Object related to a joke datum
 */
data class JokeBo(val id: Int, val joke: String, val categories: List<String>)

/**
 * A class which models any failure coming from the 'domain-layer'
 */
sealed class FailureBo(var msg: String = DEFAULT_STRING_RESOURCE) {
    object NoConnection : FailureBo(msg = ErrorMessage.ERROR_NO_CONNECTION)
    class InputParamsError(msg: String) : FailureBo(msg = msg)
    class RequestError(msg: String) : FailureBo(msg = msg)
    class ServerError(msg: String) : FailureBo(msg = msg)
    object NoData : FailureBo(msg = ErrorMessage.ERROR_NO_DATA)
    object Unknown : FailureBo(msg = ErrorMessage.ERROR_UNKNOWN)
    class Error(msg: String) : FailureBo(msg = msg)
}

/**
 * This object gathers all error messages available throughout the app
 */
object ErrorMessage {
    const val ERROR_NO_CONNECTION = "No Connection"
    const val ERROR_PARAMS_CANNOT_BE_EMPTY = "Params cannot be empty"
    const val ERROR_PARAMS_BOTH_EMAIL_PASSWORD_REQUIRED = "Both e-mail and password are required"
    const val ERROR_BAD_REQUEST = "Bad Request"
    const val ERROR_LOGIN_REQUEST = "Login: wrong e-mail or password"
    const val ERROR_LOGIN_CORRUPT_REQUEST = "Login: the user data not match"
    const val ERROR_LOGIN_RESPONSE = "Login Request Error"
    const val ERROR_REGISTER_REQUEST = "Register Request Error"
    const val ERROR_REGISTER_REQUEST_DUPLICATED = "Register: e-mail already registered"
    const val ERROR_REGISTER_REQUEST_PASSWORD = "Register: a 6-digits password is required"
    const val ERROR_GET_SESSION_SHARED_PREFERENCES = "Session: shared preferences not able"
    const val ERROR_GET_SESSION_SHARED_PREFERENCES_EDITABLE = "Session: shared preferences can not be editable"
    const val ERROR_GET_SESSION_SHARED_PREFERENCES_COMMIT_ERROR = "Session: shared preferences commit has failed"
    const val ERROR_GET_SESSION_SHARED_PREFERENCES_CORRUPTED = "Session: shared preferences saved is corrupted"
    const val ERROR_SOCKET_TIMEOUT_EXCEPTION = "Socket Timeout"
    const val ERROR_NO_DATA = "No Data"
    const val ERROR_SERVER = "Server Error"
    const val ERROR_UNKNOWN = "Unknown Error"
}
