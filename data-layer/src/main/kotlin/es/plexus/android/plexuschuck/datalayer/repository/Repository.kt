package es.plexus.android.plexuschuck.datalayer.repository

import arrow.core.Either
import es.plexus.android.plexuschuck.datalayer.datasource.AuthenticationDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.JokesDataSource
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.boToDto
import es.plexus.android.plexuschuck.datalayer.domain.dtoToBoFailure
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBoWrapper
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo

object Repository : DomainlayerContract.Datalayer.AuthenticationRepository<UserLoginBo, Boolean>,
    DomainlayerContract.Datalayer.DataRepository<JokeBoWrapper> {

    lateinit var authenticationDataSource: AuthenticationDataSource
    lateinit var jokesDataSource: JokesDataSource

    override fun loginUser(params: UserLoginBo): Either<FailureBo, Boolean> =
        authenticationDataSource.requestLogin(userData = params.boToDto())
            .mapLeft { FailureDto.FirebaseLoginError.dtoToBoFailure() }

    override fun registerUser(params: UserLoginBo): Either<FailureBo, Boolean> =
        authenticationDataSource.requestRegister(userData = params.boToDto())
            .mapLeft { FailureDto.FirebaseRegisterError(msgRes = it.msgRes).dtoToBoFailure() }

    override suspend fun fetchJokes(): Either<FailureBo, JokeBoWrapper> =
        jokesDataSource.fetchJokesResponse()

}