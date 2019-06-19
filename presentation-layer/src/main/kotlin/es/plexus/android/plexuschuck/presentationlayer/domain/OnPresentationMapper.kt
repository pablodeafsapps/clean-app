package es.plexus.android.plexuschuck.presentationlayer.domain

import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo

fun FailureBo.boToVoFailure(): FailureVo {
    return when (this) {
        is FailureBo.ServerError -> FailureVo.ServerError(code = code, msg = msg)
        is FailureBo.InputParamsError -> FailureVo.InputParamsError(msg = msg)
        FailureBo.Unknown -> FailureVo.Unknown
    }
}

fun List<JokeBo>.boToVo(): List<JokeVo> = map { it.boToVo() }

private fun JokeBo.boToVo(): JokeVo =
    JokeVo(id = id, joke = joke, categories = categories)