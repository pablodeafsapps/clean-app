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
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class LoginUserApiUcTest : KoinTest {

    private val scope = CoroutineScope(Dispatchers.Unconfined)
    private val loginUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean> by inject(named("loginUserApiUc"))
    // mocking a 'loginUserApiUc' dependency
    private val mockRepository = mock<DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>>()

    @Before
    fun setUp() {
        // adding that dependency to the DI graph, since it is in a different module (overriding)
        startKoin {
            modules(listOf(domainLayerModule, module { single(named("firebaseRepository")) { mockRepository } }))
        }
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
}