package es.plexus.android.plexuschuck.datalayer.domain

import com.google.gson.annotations.SerializedName

data class JokeDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("joke") val joke: String?,
    @SerializedName("categories") val categories: List<String>?
)

data class JokeDtoWrapper(val type: String, val value: List<JokeDto>)

sealed class FailureDto(val msg: String) {
    object FirebaseLoginError : FailureDto("login: wrong e-mail or password")
    class FirebaseRegisterError(msg: String) : FailureDto(msg)
    class Error(val code: Int, msg: String) : FailureDto(msg)
    object Unknown : FailureDto("Unknown error")
}