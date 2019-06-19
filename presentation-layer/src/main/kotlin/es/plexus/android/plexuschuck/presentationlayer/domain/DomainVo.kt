package es.plexus.android.plexuschuck.presentationlayer.domain

sealed class FailureVo(private var msg: String = "n/a") {

    abstract fun getErrorMessage(): String

    class ServerError(val code: Int, private val msg: String) : FailureVo(msg) {
        override fun getErrorMessage() = "$code - $msg"
    }

    class InputParamsError(private val msg: String) : FailureVo(msg) {
        override fun getErrorMessage() = msg
    }

    object Unknown : FailureVo() {

        private const val ERROR_MESSAGE: String = "Unknown error"

        override fun getErrorMessage(): String = ERROR_MESSAGE
    }

}