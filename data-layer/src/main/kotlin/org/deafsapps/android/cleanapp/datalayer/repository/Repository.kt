package org.deafsapps.android.cleanapp.datalayer.repository

import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.base.FailureDto
import org.deafsapps.android.cleanapp.datalayer.domain.dtoToBoFailure
import org.deafsapps.android.cleanapp.datalayer.domain.dtoToBoJoke
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object Repository : DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>,
    DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>>, KoinComponent {

    private val firebaseDataSource: DataLayerContract.FirebaseDataSource by inject("firebaseDataSource")
    private val icndbDataSource: DataLayerContract.IcndbDataSource by inject("icndbDataSource")

    override fun loginUser(params: List<String>): Either<FailureBo, Boolean> {
        return firebaseDataSource.requestFirebaseLogin(email = params[0], password = params[1])?.let {
            if (it) Either.Right(it) else Either.Left(FailureDto.FirebaseLoginError.dtoToBoFailure())
        } ?: run {
            Either.Left(FailureDto.Unknown.dtoToBoFailure())
        }
    }

    override fun registerUser(params: List<String>): Either<FailureBo, Boolean> {
        return firebaseDataSource.requestFirebaseRegister(email = params[0], password = params[1])?.let {
            if (it) Either.Right(it) else Either.Left(FailureDto.FirebaseRegisterError.dtoToBoFailure())
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
                in 300..399 -> Either.Left(FailureDto.Unknown.dtoToBoFailure())
                in 400..499 -> Either.Left(FailureDto.Unknown.dtoToBoFailure())
                in 500..599 -> Either.Left(FailureDto.Unknown.dtoToBoFailure())
                else -> Either.Left(FailureDto.Unknown.dtoToBoFailure())
            }
        }
    }

}