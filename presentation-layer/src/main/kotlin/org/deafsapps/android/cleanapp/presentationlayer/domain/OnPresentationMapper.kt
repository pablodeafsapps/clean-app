package org.deafsapps.android.cleanapp.presentationlayer.domain

import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.deafsapps.android.cleanapp.presentationlayer.base.FailureVo

fun boToVoFailure(serverError: FailureBo): FailureVo {
    return when (serverError) {
        is FailureBo.ServerError -> FailureVo.ServerError(serverError.msg)
        FailureBo.Unknown -> FailureVo.Unknown
    }
}

fun boToVo(list: List<JokeBo>): List<JokeVo> {
    return list.map { boToVo(it) }
}

fun boToVo(jokeBo: JokeBo): JokeVo =
    JokeVo(id = jokeBo.id, joke = jokeBo.joke, categories = jokeBo.categories)