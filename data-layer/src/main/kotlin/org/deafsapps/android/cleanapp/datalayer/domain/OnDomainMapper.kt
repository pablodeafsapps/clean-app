package org.deafsapps.android.cleanapp.datalayer.domain

import org.deafsapps.android.cleanapp.datalayer.base.FailureDto
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo

fun FailureDto.dtoToBoFailure(): FailureBo =
    when (this) {
        FailureDto.FirebaseLoginError -> FailureBo.ServerError(code = 0, msg = msg)
        FailureDto.FirebaseRegisterError -> FailureBo.ServerError(code = 0, msg = msg)
        is FailureDto.Error -> FailureBo.ServerError(code = code, msg = msg)
        FailureDto.Unknown -> FailureBo.Unknown
    }

fun List<JokeDto>.dtoToBoJoke(): List<JokeBo> =
    map { jokeDto ->
        JokeBo(id = jokeDto.id, joke = jokeDto.joke, categories = jokeDto.categories)
    }