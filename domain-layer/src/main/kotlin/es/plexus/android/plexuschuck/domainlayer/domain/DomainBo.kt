package es.plexus.android.plexuschuck.domainlayer.domain

data class JokeBo(val id: Int, val joke: String, val categories: List<String>)

sealed class FailureBo(var msg: String = "n/a") {

    class ServerError(val code: Int, msg: String) : FailureBo(msg)
    class InputParamsError(msg: String) : FailureBo(msg)
    object Unknown : FailureBo()

}