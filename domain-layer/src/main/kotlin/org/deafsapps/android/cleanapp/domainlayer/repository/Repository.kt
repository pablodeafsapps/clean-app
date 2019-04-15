package org.deafsapps.android.cleanapp.domainlayer.repository

import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class Repository : DomainLayerContract.Repository, KoinComponent {

    private val firebaseDataSource: DataLayerContract.DataSource by inject("firebaseDataSource")

    override fun loginUser(): Either<Failure, Boolean> {
        return firebaseDataSource.request("", "")?.let {
            Either.Right(it)
        } ?: run {
            Either.Left(Failure.FirebaseLoginError)
        }
    }

}