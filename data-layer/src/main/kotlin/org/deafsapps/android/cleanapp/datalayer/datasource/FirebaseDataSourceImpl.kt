package org.deafsapps.android.cleanapp.datalayer.datasource

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.koin.standalone.KoinComponent
import timber.log.Timber

class FirebaseDataSourceImpl : DataLayerContract.FirebaseDataSource, KoinComponent {

    private val fbAuth: FirebaseAuth? by lazy { FirebaseAuth.getInstance() }

    override fun requestFirebaseLogin(email: String, password: String): Boolean? {
        return fbAuth?.run {
            try {
                Tasks.await<AuthResult>(signInWithEmailAndPassword(email, password)).user != null
            } catch (e1: Exception) {
                Timber.w("login: ${e1.message}")
                null
            }
        }
    }

    override fun requestFirebaseRegister(email: String, password: String): Boolean? {
        return fbAuth?.run {
            try {
                Tasks.await<AuthResult>(createUserWithEmailAndPassword(email, password)).user != null
            } catch (e1: Exception) {
                Timber.w( "register: ${e1.message}")
                null
            }
        }
    }

}