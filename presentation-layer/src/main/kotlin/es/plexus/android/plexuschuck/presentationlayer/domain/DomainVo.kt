package es.plexus.android.plexuschuck.presentationlayer.domain

import android.os.Parcelable
import androidx.annotation.StringRes
import es.plexus.android.plexuschuck.domainlayer.domain.ErrorMessage
import es.plexus.android.plexuschuck.presentationlayer.feature.main.view.adapter.CnJokeView
import kotlinx.android.parcel.Parcelize

/**
 * This data class represents the Visual Object related to a user login datum
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
data class UserLoginVo(val email: String?, val password: String?)

/**
 * This data class represents the Visual Object related to a joke datum
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
@Parcelize
data class JokeVo(val id: Int?, val joke: String?, val categories: List<String>?) : CnJokeView.JokeTypeOne(), Parcelable

/**
 * This sealed class contains the 'failure' type definitions to be used in the 'presentation-layer' module
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
sealed class FailureVo(var msg: String?) {

    companion object {
        private const val DEFAULT_STRING_RESOURCE = "none"
    }

    fun getErrorMessage(): String = msg ?: DEFAULT_STRING_RESOURCE

    object NoConnection : FailureVo(msg = ErrorMessage.ERROR_NO_CONNECTION)
    object NoData : FailureVo(msg = ErrorMessage.ERROR_NO_DATA)
    object Unknown : FailureVo(msg = ErrorMessage.ERROR_UNKNOWN)
    class Error(msg: String) : FailureVo(msg = msg)

}