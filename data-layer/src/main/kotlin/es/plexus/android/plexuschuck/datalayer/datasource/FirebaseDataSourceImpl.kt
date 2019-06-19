package es.plexus.android.plexuschuck.datalayer.datasource

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.*
import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.domainlayer.base.Either
import timber.log.Timber

class FirebaseDataSourceImpl : DataLayerContract.FirebaseDataSource {

    private val fbAuth: FirebaseAuth? by lazy { FirebaseAuth.getInstance() }

    override fun requestFirebaseLogin(email: String, password: String): Either<FailureDto, Boolean>? {
        return fbAuth?.run {
            try {
                val success = Tasks.await<AuthResult>(signInWithEmailAndPassword(email, password)).user != null
                Either.Right(success)
            } catch (e: Exception) {
                when (e.cause) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        Timber.w("login: wrong e-mail or password")
                        Either.Left(FailureDto.FirebaseLoginError)
                    }
                    else -> {
                        Timber.e("login: ${e.message}")
                        Either.Left(FailureDto.Unknown)
                    }
                }
            }
        }
    }

    override fun requestFirebaseRegister(email: String, password: String): Either<FailureDto, Boolean>? {
        return fbAuth?.run {
            try {
                val success = Tasks.await<AuthResult>(createUserWithEmailAndPassword(email, password)).user != null
                Either.Right(success)
            } catch (e: Exception) {
                when (e.cause) {
                    is FirebaseAuthUserCollisionException -> {
                        Timber.w("register: e-mail already registered")
                        Either.Left(FailureDto.FirebaseRegisterError("register: e-mail already registered"))
                    }
                    is FirebaseAuthWeakPasswordException -> {
                        Timber.w("register: a 6-digits password is required")
                        Either.Left(FailureDto.FirebaseRegisterError("register: a 6-digits password is required"))
                    }
                    else -> {
                        Timber.e("register: ${e.message}")
                        Either.Left(FailureDto.FirebaseRegisterError("register: ${e.message}"))
                    }
                }
            }
        }
    }

}