package org.deafsapps.android.cleanapp.domainlayer.repository

import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.base.FailureDto
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.dtoToBoFailure
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object Repository : DomainLayerContract.Repository<String>, KoinComponent {

    private val firebaseDataSource: DataLayerContract.DataSource by inject("firebaseDataSource")

    override fun loginUser(params: List<String>): Either<FailureBo, Boolean> {
        return firebaseDataSource.requestLogin(email = params[0], password = params[1])?.let {
            if (it) Either.Right(it) else Either.Left(dtoToBoFailure(FailureDto.FirebaseLoginError))
        } ?: run {
            Either.Left(dtoToBoFailure(FailureDto.Unknown))
        }
    }

    override fun registerUser(params: List<String>): Either<FailureBo, Boolean> {
        return firebaseDataSource.requestRegister(email = params[0], password = params[1])?.let {
            if (it) Either.Right(it) else Either.Left(dtoToBoFailure(FailureDto.FirebaseRegisterError))
        } ?: run {
            Either.Left(dtoToBoFailure(FailureDto.Unknown))
        }
    }

}