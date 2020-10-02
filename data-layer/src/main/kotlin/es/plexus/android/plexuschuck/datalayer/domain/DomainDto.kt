package es.plexus.android.plexuschuck.datalayer.domain

import androidx.annotation.StringRes
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import es.plexus.android.plexuschuck.domainlayer.R
import okhttp3.ResponseBody

data class UserLoginDto(val email: String, val password: String)

data class JokeDtoWrapper(val type: String, val value: List<JokeDto>)

@JsonClass(generateAdapter = true)
data class JokeDto(
    @Json(name = "id") val id: Int?,
    @Json(name = "joke") val joke: String?,
    @Json(name = "categories") val categories: List<String>?
)

sealed class FailureDto(@StringRes val msgRes: Int?) {

    companion object {
        private const val DEFAULT_ERROR_CODE = -1
    }

    object NoConnection : FailureDto(msgRes = R.string.error_no_connection)
    class RequestError(val code: Int = DEFAULT_ERROR_CODE, msgRes: Int?, val errorBody: ResponseBody? = null) : FailureDto(msgRes = msgRes)
    object FirebaseLoginError : FailureDto(msgRes = R.string.error_login_request)
    class FirebaseRegisterError(msgRes: Int?) : FailureDto(msgRes = msgRes)
    class Error(msgRes: Int?) : FailureDto(msgRes = msgRes)
    object NoData : FailureDto(msgRes = R.string.error_no_data)
    object Unknown : FailureDto(msgRes = R.string.error_unknown)
}