package org.deafsapps.android.cleanapp.datalayer.datasource

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.di.dataLayerModule
import org.deafsapps.android.cleanapp.utils.isNetworkConnected
import org.koin.core.Koin
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FirebaseDataSource : DataLayerContract.DataSource, KoinComponent {

    private val context: Context by inject("context")
    private val fbAuth: FirebaseAuth? by lazy { FirebaseAuth.getInstance() }

    override fun request(email: String, password: String): Boolean? {
        val fbResult = fbAuth?.signInWithEmailAndPassword(email, password)?.result

        return false
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