package es.plexus.android.plexuschuck.datalayer.repository

import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.base.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.dtoToBoFailure
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.base.FailureBo
import es.plexus.android.plexuschuck.domainlayer.di.FIREBASE_DATA_SOURCE_TAG
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object Repository : DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>, KoinComponent {

    private val firebaseDataSource: DataLayerContract.FirebaseDataSource? by inject(name = FIREBASE_DATA_SOURCE_TAG)

    override fun loginUser(params: List<String>): Either<FailureBo, Boolean> {
        return firebaseDataSource?.requestFirebaseLogin(email = params[0], password = params[1])?.let { response ->
            when (response) {
                is Either.Right -> Either.Right(response.b)
                is Either.Left -> Either.Left(FailureDto.FirebaseLoginError.dtoToBoFailure())
            }
        } ?: run {
            Either.Left(FailureDto.Unknown.dtoToBoFailure())
        }
    }

    override fun registerUser(params: List<String>): Either<FailureBo, Boolean> {
        return firebaseDataSource?.requestFirebaseRegister(email = params[0], password = params[1])?.let { response ->
            when (response) {
                is Either.Right -> Either.Right(response.b)
                is Either.Left -> Either.Left(FailureDto.FirebaseRegisterError(response.a.msg).dtoToBoFailure())
            }
        } ?: run {
            Either.Left(FailureDto.Unknown.dtoToBoFailure())
        }
    }

}