package es.plexus.android.plexuschuck.presentationlayer.domain

import android.os.Parcelable
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter.CnJokeViewType
import kotlinx.android.parcel.Parcelize

/**
 * This sealed class contains the 'failure' type definitions to be used in the 'presentation-layer' module
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
sealed class FailureVo(private var msg: String = "n/a") {

    abstract fun getErrorMessage(): String

    class NoDataError(private val msg: String) : FailureVo(msg) {
        override fun getErrorMessage() = msg
    }
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

/**
 * This data class represents the Visual Object related to a joke datum
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
@Parcelize
data class JokeVo(val id: Int?, val joke: String?, val categories: List<String>?) : CnJokeViewType.JokeTypeOne(), Parcelable