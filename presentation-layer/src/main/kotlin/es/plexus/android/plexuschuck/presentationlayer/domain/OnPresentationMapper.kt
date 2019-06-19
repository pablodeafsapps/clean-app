package es.plexus.android.plexuschuck.presentationlayer.domain

import es.plexus.android.plexuschuck.domainlayer.base.FailureBo

fun FailureBo.boToVoFailure(): FailureVo {
    return when (this) {
        is FailureBo.ServerError -> FailureVo.ServerError(code = code, msg = msg)
        is FailureBo.InputParamsError -> FailureVo.InputParamsError(msg = msg)
        FailureBo.Unknown -> FailureVo.Unknown
    }
}