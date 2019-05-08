package org.deafsapps.android.cleanapp.datalayer

interface DataLayerContract {

    interface DataSource {

        fun requestLogin(email: String, password: String): Boolean?
        fun requestRegister(email: String, password: String): Boolean?

    }

}