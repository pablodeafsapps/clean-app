package es.plexus.android.plexuschuck.datalayer.repository

import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.FIREBASE_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.ICNDB_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.domain.FailureDto
import es.plexus.android.plexuschuck.datalayer.domain.dtoToBoFailure
import es.plexus.android.plexuschuck.datalayer.domain.dtoToBoJoke
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object Repository : DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>,
    DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>>, KoinComponent {

    private val firebaseDataSource: DataLayerContract.FirebaseDataSource? by inject(name = FIREBASE_DATA_SOURCE_TAG)
    private val icndbDataSource: DataLayerContract.IcndbDataSource by inject(name = ICNDB_DATA_SOURCE_TAG)

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

    override suspend fun fetchJokes(params: List<String>?): Either<FailureBo, List<JokeBo>> {
        val queryResponse = icndbDataSource.fetchIcndbJokesResponse(params = params)
        return if (queryResponse.isSuccessful) {
            val jokeList = queryResponse.body()?.value?.dtoToBoJoke()
            jokeList?.let { Either.Right(jokeList) } ?: run { Either.Left(FailureDto.Unknown.dtoToBoFailure()) }
        } else {
            when (queryResponse.code()) {
                in 300..599 -> Either.Left(
                    FailureDto.Error(code = queryResponse.code(), msg = queryResponse.message()).dtoToBoFailure()
                )
                else -> Either.Left(FailureDto.Unknown.dtoToBoFailure())
            }
        }
    }

}