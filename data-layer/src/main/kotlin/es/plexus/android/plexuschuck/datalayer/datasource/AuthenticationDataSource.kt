package es.plexus.android.plexuschuck.datalayer.datasource

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.UserLoginDto
import es.plexus.android.plexuschuck.datalayer.domain.UserSessionDto
import es.plexus.android.plexuschuck.datalayer.domain.toUserSessionDto
import es.plexus.android.plexuschuck.domainlayer.domain.ErrorMessage
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo

/**
 * This interface represents the contract to be complied by an entity to fit in as the authentication
 * system provider
 */
interface AuthenticationDataSource {

    companion object {
        const val AUTHENTICATION_DATA_SOURCE_TAG = "authenticationDataSource"
        const val AUTHENTICATOR_TAG = "authenticationDataSource"
    }

    /**
     * Requests a user login and returns the user session dto. If something went wrong,
     * an error is returned.
     * @param [userData] the user data transfer object to login
     * @return An [UserSessionDto] if successful or an [FailureBo] otherwise
     */
    fun requestLogin(userData: UserLoginDto): Either<FailureDto, UserSessionDto>

    /**
     * Requests a user register and returns whether it was successful or not. If something went wrong,
     * an error is returned.
     * @param [userData] the user data transfer object to register
     * @return A [Boolean] if successful or an [FailureBo] otherwise
     */
    fun requestRegister(userData: UserLoginDto): Either<FailureDto, Boolean>

    /**
     * Requests a user logout and returns whether it was successful or not. If something went wrong,
     * an error is returned.
     * @return An [Unit] if successful or an [FailureBo] otherwise
     */
    fun requestLogout(): Either<FailureDto, Unit>
}

/**
 * This class complies with [AuthenticationDataSource] so that it is in charge of providing any required
 * authentication check or query
 */
class FirebaseDataSource(private val fbAuth: FirebaseAuth) : AuthenticationDataSource {

    override fun requestLogin(userData: UserLoginDto): Either<FailureDto, UserSessionDto> =
        try {
            (Tasks.await(
                fbAuth.signInWithEmailAndPassword(userData.email, userData.password)
            )).user?.let { firebaseUser ->
                    if (firebaseUser.email == userData.email) {
                        firebaseUser.toUserSessionDto().right()
                    } else {
                        FailureDto.FirebaseLoginCorruptError.left()
                    }
                } ?: run {
                FailureDto.FirebaseLoginError.left()
            }
        } catch (e: Exception) {
            when (e.cause) {
                is FirebaseAuthInvalidCredentialsException -> {
                    Log.w("requestLogin", "login: wrong e-mail or password")
                    FailureDto.FirebaseLoginError.left()
                }
                else -> {
                    Log.e("requestLogin", "login: ${e.message}")
                    FailureDto.Unknown.left()
                }
            }
        }

    override fun requestRegister(userData: UserLoginDto): Either<FailureDto, Boolean> =
        try {
            (Tasks.await<AuthResult>(
                fbAuth.createUserWithEmailAndPassword(userData.email, userData.password)
            ).user != null).right()
        } catch (e: Exception) {
            when (e.cause) {
                is FirebaseAuthUserCollisionException -> {
                    Log.w("requestRegister", "register: e-mail already registered")
                    FailureDto.FirebaseRegisterError(msg = ErrorMessage.ERROR_REGISTER_REQUEST_DUPLICATED)
                        .left()
                }
                is FirebaseAuthWeakPasswordException -> {
                    Log.w("requestRegister", "register: a 6-digits password is required")
                    FailureDto.FirebaseRegisterError(msg = ErrorMessage.ERROR_REGISTER_REQUEST_PASSWORD)
                        .left()
                }
                else -> {
                    Log.e("requestRegister", "register: ${e.message}")
                    FailureDto.FirebaseRegisterError(msg = ErrorMessage.ERROR_REGISTER_REQUEST)
                        .left()
                }
            }
        }

    override fun requestLogout(): Either<FailureDto, Unit> =
        try {
            fbAuth.signOut().right()
        } catch (e: Exception) {
            Log.e("requestLogout", "logout: ${e.message}")
            FailureDto.Unknown.left()
        }

}
