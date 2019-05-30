package org.deafsapps.android.cleanapp.domainlayer.usecase

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.runBlocking
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.di.domainLayerModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

class LoginUserApiUcTest : KoinTest {

    private val loginUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean> by inject("loginUserApiUc")
    // mocking a 'loginUserApiUc' dependency
    private val repository = mock<DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>>()

    @Before
    fun setUp() {
        // adding that dependency to the DI graph, since it is in a different module (overriding)
        startKoin(listOf(domainLayerModule, module { single { repository } }))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check that if params List is not null, loginUser is invoked`() = runBlocking<Unit> {
        val paramsList = listOf("user", "password")
        val dummyCallback = { _: Either<FailureBo, Boolean> -> }

        loginUserApiUc.invoke(params = paramsList, onResult = dummyCallback)
        verify(repository).loginUser(params = eq(paramsList))
    }

}