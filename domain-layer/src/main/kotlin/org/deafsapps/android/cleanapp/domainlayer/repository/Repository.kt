package org.deafsapps.android.cleanapp.domainlayer.repository

import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object Repository : DomainLayerContract.Repository<String>, KoinComponent {

    private val firebaseDataSource: DataLayerContract.DataSource by inject("firebaseDataSource")

    override fun loginUser(params: List<String>): Either<Failure, Boolean> {
        return firebaseDataSource.request(email = params[0], password = params[1])?.let {
            if (it) Either.Right(it) else Either.Left(Failure.FirebaseLoginError)
        } ?: run {
            Either.Left(Failure.Unknown)
        }
    }

}