package org.deafsapps.android.cleanapp.domainlayer.domain

import org.deafsapps.android.cleanapp.datalayer.base.FailureDto
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo

fun dtoToBoFailure(serverError: FailureDto): FailureBo {
    return when (serverError) {
        FailureDto.FirebaseLoginError -> FailureBo.ServerError(serverError.msg)
        FailureDto.FirebaseRegisterError -> FailureBo.ServerError(serverError.msg)
        FailureDto.Unknown -> FailureBo.Unknown
    }
}