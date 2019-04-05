package org.deafsapps.android.cleanapp.domainlayer.feature

import org.deafsapps.android.cleanapp.domainlayer.base.DomainLayerBridge

interface LoginDomainLayerBridge : DomainLayerBridge {

    fun loginUser()

    fun registerUser()

}