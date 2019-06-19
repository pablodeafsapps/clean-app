package es.plexus.android.plexuschuck.datalayer.domain

data class JokeDto(val id: Int, val joke: String, val categories: List<String>)

data class JokeDtoWrapper(val type: String, val value: List<JokeDto>)

sealed class FailureDto(val msg: String) {

    object FirebaseLoginError : FailureDto("login: wrong e-mail or password")
    class FirebaseRegisterError(msg: String) : FailureDto(msg)
    class Error(val code: Int, msg: String) : FailureDto(msg)
    object Unknown : FailureDto("Unknown error")

}
