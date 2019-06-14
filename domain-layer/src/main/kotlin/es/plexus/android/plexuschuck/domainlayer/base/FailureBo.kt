package es.plexus.android.plexuschuck.domainlayer.base

sealed class FailureBo(var msg: String = "n/a") {

    class ServerError(val code: Int, msg: String) : FailureBo(msg)
    object Unknown : FailureBo()

}