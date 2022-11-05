package org.deafsapps.android.cleanapp.datalayer.domain

import com.google.firebase.auth.FirebaseUser
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserSessionBo
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo

private const val DEFAULT_INTEGER_VALUE = 0
private const val DEFAULT_STRING_VALUE = ""

/**
 * Maps a [UserLoginBo] into a [UserLoginDto]
 */
fun UserLoginBo.boToDto() = UserLoginDto(
    email = email,
    password = password
)

/**
 * Maps a [UserSessionDto] into a [UserSessionBo]
 */
fun UserSessionDto.dtoToBo(lastAccess: Long) = UserSessionBo(
    uuid = uuid,
    email = email,
    name = name,
    lastAccess = lastAccess
)

/**
 * Maps a [UserSessionBo] into a [UserSessionDto]
 */
fun UserSessionBo.boToDto() = UserSessionDto(
    uuid = uuid,
    email = email,
    name = name
)

/**
 * Maps a [FirebaseUser] into a [UserSessionDto]
 */
fun FirebaseUser.toUserSessionDto() = UserSessionDto(
    uuid = uid,
    name = displayName ?: DEFAULT_STRING_VALUE,
    email = email ?: DEFAULT_STRING_VALUE
)

/**
 * Maps a [JokeDtoWrapper] into a [JokeBoWrapper]
 */
fun JokeDtoWrapper.dtoToBo() = JokeBoWrapper(
    type = type,
    value = value.jokeListDtoToBo()
)

private fun List<JokeDto>.jokeListDtoToBo() = map { it.dtoToBo() }

private fun JokeDto.dtoToBo() = JokeBo(
    id = id ?: DEFAULT_INTEGER_VALUE,
    joke = joke ?: DEFAULT_STRING_VALUE,
    categories = categories ?: emptyList()
)

/**
 * Maps a [FailureDto] into a [FailureBo]
 */
fun FailureDto.dtoToBoFailure(): FailureBo = when (this) {
    FailureDto.NoConnection -> FailureBo.NoConnection
    is FailureDto.RequestError -> FailureBo.RequestError(msg = msg ?: DEFAULT_STRING_VALUE)
    FailureDto.FirebaseLoginError -> FailureBo.ServerError(msg = msg ?: DEFAULT_STRING_VALUE)
    is FailureDto.FirebaseRegisterError -> FailureBo.ServerError(msg = msg ?: DEFAULT_STRING_VALUE)
    is FailureDto.Error -> FailureBo.ServerError(msg = msg ?: DEFAULT_STRING_VALUE)
    is FailureDto.FirebaseLoginCorruptError -> FailureBo.Error(msg = msg ?: DEFAULT_STRING_VALUE)
    FailureDto.NoData -> FailureBo.NoData
    FailureDto.Unknown -> FailureBo.Unknown
}
