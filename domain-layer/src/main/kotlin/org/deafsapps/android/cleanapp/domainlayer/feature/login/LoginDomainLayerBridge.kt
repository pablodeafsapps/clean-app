package org.deafsapps.android.cleanapp.domainlayer.feature.login

import arrow.core.Either
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserSessionBo
import kotlinx.coroutines.CoroutineScope

const val LOGIN_DOMAIN_BRIDGE_TAG = "loginDomainLayerBridge"

/**
 * An entity which gathers all the available functionality related to the 'login' feature
 */
interface LoginDomainLayerBridge<in T> : BaseDomainLayerBridge {

    fun saveUsers(scope: CoroutineScope, onResult: (Either<FailureBo, Boolean>) -> Unit = {})

    fun fetchUsers(scope: CoroutineScope, onResult: (Either<FailureBo, String>) -> Unit = {})

    /**
     * A function blueprint to log-in a user
     *
     * @param [scope] The [CoroutineScope] associated, which can be used to handle an enclosing Kotlin Coroutine
     * @param [onResult] A callback to send back any data of interest
     */
    fun loginUser(scope: CoroutineScope, params: T, onResult: (Either<FailureBo, Boolean>) -> Unit = {})

    /**
     * A function blueprint to register a user
     *
     * @param [scope] The [CoroutineScope] associated, which can be used to handle an enclosing Kotlin Coroutine
     * @param [onResult] A callback to send back any data of interest
     */
    fun registerUser(scope: CoroutineScope, params: T, onResult: (Either<FailureBo, Boolean>) -> Unit = {})

    /**
     * A function blueprint to fetch the session of a user
     *
     * @param [scope] The [CoroutineScope] associated, which can be used to handle an enclosing Kotlin Coroutine
     * @param [onResult] A callback to send back any data of interest
     */
    fun fetchSessionUser(scope: CoroutineScope, onResult: (Either<FailureBo, UserSessionBo>) -> Unit = {})
}

internal class LoginDomainLayerBridgeImpl(
    private val saveUsersUc: DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean>,
    private val fetchUsersUc: DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, String>,
    private val loginUserUc: DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean>,
    private val registerUserUc: DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean>,
    private val fetchSessionUser: DomainlayerContract.Presentationlayer.UseCase<Any, UserSessionBo>
) : LoginDomainLayerBridge<UserLoginBo> {

    override fun saveUsers(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        saveUsersUc.invoke(scope = scope, onResult = onResult)
    }

    override fun fetchUsers(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, String>) -> Unit
    ) {
        fetchUsersUc.invoke(scope = scope, onResult = onResult)
    }

    override fun loginUser(
        scope: CoroutineScope,
        params: UserLoginBo,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        loginUserUc.invoke(scope = scope, params = params, onResult = onResult)
    }

    override fun registerUser(
        scope: CoroutineScope,
        params: UserLoginBo,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        registerUserUc.invoke(scope = scope, params = params, onResult = onResult)
    }

    override fun fetchSessionUser(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, UserSessionBo>) -> Unit
    ) {
        fetchSessionUser.invoke(scope = scope, onResult = onResult)
    }

}
