package org.deafsapps.android.cleanapp.presentationlayer.domain

import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.deafsapps.android.cleanapp.presentationlayer.base.FailureVo

fun FailureBo.boToVoFailure(): FailureVo {
    return when (this) {
        is FailureBo.ServerError -> FailureVo.ServerError(msg)
        FailureBo.Unknown -> FailureVo.Unknown
    }
}

fun List<JokeBo>.boToVo(): List<JokeVo> = map { it.boToVo() }

private fun JokeBo.boToVo(): JokeVo =
    JokeVo(id = id, joke = joke, categories = categories)