package org.deafsapps.android.cleanapp.presentationlayer.domain

import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.presentationlayer.base.FailureVo

fun boToVoFailure(serverError: FailureBo): FailureVo {
    return when (serverError) {
        is FailureBo.ServerError -> FailureVo.ServerError(serverError.msg)
        FailureBo.Unknown -> FailureVo.Unknown
    }

}