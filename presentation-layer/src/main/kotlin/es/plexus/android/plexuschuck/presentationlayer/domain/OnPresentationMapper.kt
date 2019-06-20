package es.plexus.android.plexuschuck.presentationlayer.domain

import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo

/**
 * Extension function which maps a failure Business Object to a failure Visual Object
 *
 * @return the [FailureVo] type equivalent datum
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
fun FailureBo.boToVoFailure(): FailureVo {
    return when (this) {
        is FailureBo.ServerError -> FailureVo.ServerError(code = code, msg = msg)
        is FailureBo.InputParamsError -> FailureVo.InputParamsError(msg = msg)
        FailureBo.Unknown -> FailureVo.Unknown
    }
}


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