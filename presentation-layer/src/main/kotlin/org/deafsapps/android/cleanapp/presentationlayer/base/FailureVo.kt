package org.deafsapps.android.cleanapp.presentationlayer.base

sealed class FailureVo(private var msg: String = "n/a") {

    abstract fun getErrorMessage(): String

    class ServerError(private val msg: String) : FailureVo(msg) {
        override fun getErrorMessage() = msg
    }

    object Unknown : FailureVo() {

        private const val ERROR_MESSAGE: String = "Unknown error"

        override fun getErrorMessage(): String = ERROR_MESSAGE
    }

}