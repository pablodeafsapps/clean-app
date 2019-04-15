package org.deafsapps.android.cleanapp.datalayer

interface DataLayerContract {

    interface DataSource {

        fun request(email: String, password: String): Boolean?

    }

}