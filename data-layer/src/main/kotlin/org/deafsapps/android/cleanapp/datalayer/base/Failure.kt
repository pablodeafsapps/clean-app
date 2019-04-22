package org.deafsapps.android.cleanapp.datalayer.base

sealed class Failure {
    object FirebaseLoginError : Failure()
    object FirebaseRegisterError : Failure()
    object Unknown : Failure()
}