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
            if (it) Either.Right(it) else Either.Left(dtoToBoFailure(FailureDto.FirebaseLoginError))
        } ?: run {
            Either.Left(dtoToBoFailure(FailureDto.Unknown))
        }
    }

    override fun registerUser(params: List<String>): Either<FailureBo, Boolean> {
        return firebaseDataSource.requestFirebaseRegister(email = params[0], password = params[1])?.let {
            if (it) Either.Right(it) else Either.Left(dtoToBoFailure(FailureDto.FirebaseRegisterError))
        } ?: run {
            Either.Left(dtoToBoFailure(FailureDto.Unknown))
        }
    }

    override suspend fun fetchJokes(params: List<String>?): Either<FailureBo, List<JokeBo>> {
        return icndbDataSource.fetchIcndbJokes(params = params)?.let {
            Either.Right(dtoToBoJoke(it))
        } ?: run {
            Either.Left(dtoToBoFailure(FailureDto.Unknown))
        }
    }

}