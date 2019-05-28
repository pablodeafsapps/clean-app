package org.deafsapps.android.cleanapp.datalayer.domain

import org.deafsapps.android.cleanapp.datalayer.base.FailureDto
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo

fun dtoToBoFailure(serverError: FailureDto): FailureBo {
    return when (serverError) {
        FailureDto.FirebaseLoginError -> FailureBo.ServerError(serverError.msg)
        FailureDto.FirebaseRegisterError -> FailureBo.ServerError(serverError.msg)
        FailureDto.Unknown -> FailureBo.Unknown
    }
}

fun dtoToBoJoke(list: List<JokeDto>): List<JokeBo> =
    list.map { jokeDto ->
        JokeBo(id = jokeDto.id, joke = jokeDto.joke, categories = jokeDto.categories)
    }