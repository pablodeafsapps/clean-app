package org.deafsapps.android.cleanapp.domainlayer.usecase

import com.nhaarman.mockito_kotlin.eq
import org.deafsapps.android.cleanapp.datalayer.base.FailureDto
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.di.domainLayerModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declareMock
import org.mockito.Mockito

class LoginUserApiUcTest : KoinTest {

    private val loginUserApiUc: DomainLayerContract.UseCase<String?> by inject("loginUserApiUc")
    private val repository: DomainLayerContract.Repository<String> by inject()

    @Before
    fun setUp() {
        startKoin(listOf(domainLayerModule))
        declareMock<DomainLayerContract.Repository<String>>()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check that if params List is not null, loginUser is invoked`() {
        val paramsList = listOf("", "")
        val dummyCallback = { _: Either<FailureBo, Boolean> -> }

        loginUserApiUc.invoke(params = paramsList, onResult = dummyCallback)
        Mockito.verify(repository).loginUser(params = eq(paramsList))
    }

}