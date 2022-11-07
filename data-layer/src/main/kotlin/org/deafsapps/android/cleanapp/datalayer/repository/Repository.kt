package org.deafsapps.android.cleanapp.datalayer.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.deafsapps.android.cleanapp.datalayer.datasource.*
import org.deafsapps.android.cleanapp.datalayer.domain.FailureDto
import org.deafsapps.android.cleanapp.datalayer.domain.boToDto
import org.deafsapps.android.cleanapp.datalayer.domain.dtoToBo
import org.deafsapps.android.cleanapp.datalayer.domain.dtoToBoFailure
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserSessionBo
import java.net.SocketTimeoutException

object Repository : DomainlayerContract.Datalayer.AuthenticationRepository<UserLoginBo, Boolean>,
        DomainlayerContract.Datalayer.SessionRepository<UserSessionBo>,
        DomainlayerContract.Datalayer.DataRepository<JokeBoWrapper>,
        DomainlayerContract.Datalayer.PersistencyRepository<String> {

    lateinit var connectivityDataSource: ConnectivityDataSource
    lateinit var authenticationDataSource: AuthenticationDataSource
    lateinit var jokesDataSource: JokesDataSource
    lateinit var sessionDataSource: SessionDataSource
    lateinit var persistenceDataSource: PersistenceDataSource

    /**
     * This method logs in a user using some personal-info parameters. It first checks whether a
     * connection is available, and if so queries the data-source and persists the data.
     *
     * @param [params] containing the mandatory data for this request
     * @return A [Boolean] indicating if the data is successfully queried and stored, or an error otherwise
     * @throws SocketTimeoutException
     */
    override suspend fun loginUser(params: UserLoginBo): Either<FailureBo, Boolean> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                authenticationDataSource.requestLogin(userData = params.boToDto()).fold({
                    FailureDto.FirebaseLoginError.dtoToBoFailure().left()
                }, {
                    sessionDataSource.saveUserSession(it).mapLeft { failure ->
                        failure.dtoToBoFailure()
                    }
                })
            } ?: run {
                FailureDto.NoConnection.dtoToBoFailure().left()
            }
        } catch (e: IllegalStateException) {
            Log.e("requestLogin(...", "Error: ${e.message}")
            FailureDto.Unknown.dtoToBoFailure().left()
        }

    /**
     * This method logout in a user using some personal-info parameters. It first checks whether a
     * connection is available, and if so queries the data-source.
     *
     * @param [UserSessionBo] containing the mandatory data for this request
     * @return A [Boolean] indicating if the data is successfully removed, or an error otherwise
     * @throws SocketTimeoutException
     */
    override suspend fun logoutUser(): Either<FailureBo, Boolean> =
        try {
            authenticationDataSource.requestLogout()
                .fold({
                    FailureDto.FirebaseLoginError.dtoToBoFailure().left()
                }, {
                    sessionDataSource.removeUserSession().mapLeft { failure ->
                        failure.dtoToBoFailure()
                    }
                })
        } catch (e: IllegalStateException) {
            Log.e("requestLogout(...", "Error: ${e.message}")
            FailureDto.Unknown.dtoToBoFailure().left()
        }

    /**
     * This method registers a user using some personal-info parameters. It first checks whether a
     * connection is available, and if so queries the data-source.
     *
     * @param [UserLoginBo] containing the mandatory data for this request
     * @return A [Boolean] value illustrating the outcome, or an error otherwise
     * @throws SocketTimeoutException
     */
    override suspend fun registerUser(params: UserLoginBo): Either<FailureBo, Boolean> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                authenticationDataSource.requestRegister(userData = params.boToDto())
                    .mapLeft { FailureDto.FirebaseRegisterError(msg = it.msg).dtoToBoFailure() }
            } ?: run {
                FailureDto.NoConnection.dtoToBoFailure().left()
            }
        } catch (e: IllegalStateException) {
            Log.e("requestRegister(...", "Error: ${e.message}")
            FailureDto.Unknown.dtoToBoFailure().left()
        }

    /**
     * This method fetch the current session user's
     *
     * @return A [UserSessionBo] value illustrating the outcome, or an error otherwise
     */
    override suspend fun sessionUser(): Either<FailureBo, UserSessionBo> =
        sessionDataSource.getUserSession().fold({ failure ->
            failure.dtoToBoFailure().left()
        }, { userSession ->
            userSession.dtoToBo(System.currentTimeMillis()).right()
        })

    /**
     * This method save a session user's
     *
     * @param [UserSessionBo] containing the mandatory data for this request
     * @return A [UserSessionBo] value illustrating the outcome, or an error otherwise
     */
    override suspend fun saveSessionUser(session: UserSessionBo): Either<FailureBo, Boolean> =
        sessionDataSource.saveUserSession(session.boToDto()).mapLeft { failure ->
            failure.dtoToBoFailure()
        }

    /**
     * This method remove a session user's
     *
     * @param [UserSessionBo] containing the mandatory data for this request
     * @return A [Boolean] value illustrating the outcome, or an error otherwise
     */
    override suspend fun removeSessionUser(): Either<FailureBo, Boolean> =
        sessionDataSource.removeUserSession().mapLeft { failure ->
            failure.dtoToBoFailure()
        }

    /**
     * This method fetches a list of jokes. It first checks whether a connection is available, and
     * if so queries the data-source.
     *
     * @return A [JokeBoWrapper] or an error otherwise
     * @throws SocketTimeoutException
     */
    override suspend fun fetchJokes(): Either<FailureBo, JokeBoWrapper> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                jokesDataSource.fetchJokesResponse()
            } ?: run {
                FailureDto.NoConnection.dtoToBoFailure().left()
            }
        } catch (e: IllegalStateException) {
            Log.e("fetchJokesResponse()", "Error: ${e.message}")
            FailureDto.Unknown.dtoToBoFailure().left()
        }

    override suspend fun saveUsers(): Either<Nothing, Boolean> {
        persistenceDataSource.saveUser()
        return true.right()
    }

    override suspend fun fetchUsers(): Either<Nothing, String> =
            persistenceDataSource.getUser().toString().right()

}
