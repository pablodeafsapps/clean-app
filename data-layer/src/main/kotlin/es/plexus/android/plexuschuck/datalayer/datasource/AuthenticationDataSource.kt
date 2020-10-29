package es.plexus.android.plexuschuck.datalayer.datasource

import android.util.Log
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.UserLoginDto
import es.plexus.android.plexuschuck.domainlayer.R

interface AuthenticationDataSource {

    companion object {
        const val AUTHENTICATION_DATA_SOURCE_TAG = "authenticationDataSource"
        const val AUTHENTICATOR_TAG = "authenticationDataSource"
    }

    fun requestLogin(userData: UserLoginDto): Either<FailureDto, Boolean>
    fun requestRegister(userData: UserLoginDto): Either<FailureDto, Boolean>

}

class FirebaseDataSource(private val fbAuth: FirebaseAuth) : AuthenticationDataSource {

    override fun requestLogin(userData: UserLoginDto): Either<FailureDto, Boolean> =
        try {
            (Tasks.await<AuthResult>(
                fbAuth.signInWithEmailAndPassword(userData.email, userData.password)
            ).user != null).right()
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
                    FailureDto.FirebaseRegisterError(msgRes = R.string.error_register_request_duplicated)
                        .left()
                }
                is FirebaseAuthWeakPasswordException -> {
                    Log.w("requestRegister", "register: a 6-digits password is required")
                    FailureDto.FirebaseRegisterError(msgRes = R.string.error_register_request_password)
                        .left()
                }
                else -> {
                    Log.e("requestRegister", "register: ${e.message}")
                    FailureDto.FirebaseRegisterError(msgRes = R.string.error_register_request_default)
                        .left()
                }
            }
        }

}