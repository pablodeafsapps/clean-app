package org.deafsapps.android.cleanapp.datalayer.datasource

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FirebaseDataSource : DataLayerContract.DataSource, KoinComponent {

    private val context: Context by inject()
    private val fbAuth: FirebaseAuth? by lazy { FirebaseAuth.getInstance() }

    override fun request(email: String, password: String): Boolean? {
        return fbAuth?.run {
            try {
                Tasks.await<AuthResult>(signInWithEmailAndPassword(email, password)).user != null
            } catch (e1: Exception) {
                Log.w("FirebaseDataSource", e1.message)
                null
            }
        }
    }

    /*
    override fun register(email: String, password: String) {
        if (isNetworkConnected(context)) {
            fbAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        output?.onRegisterSuccess()
                    } else {
                        output?.onRegisterError()
                    }
                }
        } else {
            output?.noNetworkAccess()
        }
    }

    override fun createUserIfNotExisting(userEmail: String?) {
        if (isNetworkConnected(context)) {

            fbDatabase.child("users").child(fbAuth?.currentUser?.uid).setValue(
                User(email = fbAuth?.currentUser?.email, sports = listOf())
            )
        } else {
            output?.noNetworkAccess()
        }
    }
    */

}