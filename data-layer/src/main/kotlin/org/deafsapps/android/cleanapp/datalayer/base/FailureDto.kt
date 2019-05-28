package org.deafsapps.android.cleanapp.datalayer.base

sealed class FailureDto(val msg: String) {

    object FirebaseLoginError : FailureDto("Firebase login error")
    object FirebaseRegisterError : FailureDto("Firebase register error")
    object Unknown : FailureDto("Unknown error")

}