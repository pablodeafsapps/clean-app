package es.plexus.android.plexuschuck.datalayer.domain

import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo

fun FailureDto.dtoToBoFailure(): FailureBo =
    when (this) {
        FailureDto.FirebaseLoginError -> FailureBo.ServerError(code = 0, msg = msg)
        is FailureDto.FirebaseRegisterError -> FailureBo.ServerError(code = 1, msg = msg)
        is FailureDto.Error -> FailureBo.ServerError(code = code, msg = msg)
        FailureDto.Unknown -> FailureBo.Unknown
    }

fun List<JokeDto>.dtoToBoJoke(): List<JokeBo> =
    map { jokeDto ->
        JokeBo(id = jokeDto.id, joke = jokeDto.joke, categories = jokeDto.categories)
    }
