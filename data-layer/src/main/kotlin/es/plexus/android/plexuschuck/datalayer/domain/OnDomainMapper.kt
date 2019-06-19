package es.plexus.android.plexuschuck.datalayer.domain

import es.plexus.android.plexuschuck.datalayer.base.FailureDto
import es.plexus.android.plexuschuck.domainlayer.base.FailureBo

fun FailureDto.dtoToBoFailure(): FailureBo =
    when (this) {
        FailureDto.FirebaseLoginError -> FailureBo.ServerError(code = 0, msg = msg)
        is FailureDto.FirebaseRegisterError -> FailureBo.ServerError(code = 1, msg = msg)
        is FailureDto.Error -> FailureBo.ServerError(code = code, msg = msg)
        FailureDto.Unknown -> FailureBo.Unknown
    }