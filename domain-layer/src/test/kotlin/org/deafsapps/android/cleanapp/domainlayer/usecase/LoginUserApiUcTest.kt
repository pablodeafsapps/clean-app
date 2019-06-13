package org.deafsapps.android.cleanapp.domainlayer.usecase

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.di.domainLayerModule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

class LoginUserApiUcTest : KoinTest {

    private val scope = CoroutineScope(Dispatchers.Unconfined)
    private val loginUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>
            by inject("loginUserApiUc")
    // mocking a 'loginUserApiUc' dependency
    private val mockRepository = mock<DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>>()

    @Before
    fun setUp() {
        // adding that dependency to the DI graph, since it is in a different module (overriding)
        startKoin(listOf(domainLayerModule, module { single("firebaseRepository") { mockRepository } }))
        // this next line allows to run test with coroutines using the 'Dispatchers.Main'
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        scope.cancel()
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `check that if params List is not null, loginUser is invoked`() {
        val paramsList = listOf("user", "password")
        val dummyCallback = { _: Either<FailureBo, Boolean> -> }

        runBlocking {
            loginUserApiUc.invoke(scope = scope, params = paramsList, onResult = dummyCallback)
        }

        verify(mockRepository).loginUser(params = eq(paramsList))
    }

    @Test
    fun `check that if params List is null, error is returned`() {
        val paramsList = listOf(null)
        val expectedResult = Either.Left(FailureBo.Unknown)

        runBlocking {
            Assert.assertTrue(loginUserApiUc.run(params = paramsList) == expectedResult)
        }
    }

    @Test
    fun `check that if params are insufficient, error is returned`() {
        val paramsList = listOf("user")
        val expectedResult = Either.Left(FailureBo.Unknown)

        runBlocking {
            Assert.assertTrue(loginUserApiUc.run(params = paramsList) == expectedResult)
        }
    }

}