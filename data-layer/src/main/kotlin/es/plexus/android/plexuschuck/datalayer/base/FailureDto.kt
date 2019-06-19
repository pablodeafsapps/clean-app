package es.plexus.android.plexuschuck.datalayer.base

sealed class FailureDto(val msg: String) {

    object FirebaseLoginError : FailureDto("login: wrong e-mail or password")
    class FirebaseRegisterError(msg: String) : FailureDto(msg)
    class Error(val code: Int, msg: String) : FailureDto(msg)
    object Unknown : FailureDto("Unknown error")

}