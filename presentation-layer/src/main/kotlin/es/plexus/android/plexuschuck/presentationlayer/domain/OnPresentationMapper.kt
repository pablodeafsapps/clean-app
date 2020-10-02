package es.plexus.android.plexuschuck.presentationlayer.domain

import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo

private const val DEFAULT_STRING_VALUE = "none"

fun UserLoginVo.voToBo() = UserLoginBo(
    email = email ?: DEFAULT_STRING_VALUE,
    password = password ?: DEFAULT_STRING_VALUE
)

/**
 * Extension function which maps a list of joke Business Objects to a list of joke Visual Objects
 *
 * @return the list of [JokeVo] type equivalent data
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
fun List<JokeBo>.boToVo(): List<JokeVo> = map { it.boToVo() }

private fun JokeBo.boToVo(): JokeVo =
    JokeVo(id = id, joke = joke, categories = categories)

/**
 * Extension function which maps a failure Business Object to a failure Visual Object
 *
 * @return the [FailureVo] type equivalent datum
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
fun FailureBo.boToVoFailure(): FailureVo =
    when (this) {
        is FailureBo.InputParamsError -> FailureVo.Error(msgRes = msgRes)
        is FailureBo.RequestError -> FailureVo.Error(msgRes = msgRes)
        is FailureBo.ServerError -> FailureVo.Error(msgRes = msgRes)
        is FailureBo.NoData -> FailureVo.NoData(msgRes = msgRes)
        is FailureBo.NoConnection -> FailureVo.NoConnection(msgRes = msgRes)
        is FailureBo.Unknown -> FailureVo.Unknown(msgRes = msgRes)
    }