package es.plexus.android.plexuschuck.domainlayer

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.FIREBASE_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.di.domainLayerModule
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.usecase.LOGIN_UC_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
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
            by inject(name = LOGIN_UC_TAG)
    // mocking a 'loginUserApiUc' dependency
    private val mockRepository = mock<DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>>()

    @Before
    fun setUp() {
        // adding that dependency to the DI graph, since it is in a different module (overriding)
        startKoin(listOf(domainLayerModule, module { single(name = FIREBASE_REPOSITORY_TAG) { mockRepository } }))
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
        val expectedResult = Either.Left(FailureBo.InputParamsError(msg = "Both e-mail and password are required"))

        runBlocking {
            val actualResult = loginUserApiUc.run(params = paramsList)
            Assert.assertTrue((actualResult as? Either.Left<FailureBo>)?.let {
                it.a.msg == expectedResult.a.msg
            } ?: run {
                false
            })
        }
    }

    @Test
    fun `check that if params are insufficient, error is returned`() {
        val paramsList = listOf("user")
        val expectedResult = Either.Left(FailureBo.InputParamsError(msg = "Both e-mail and password are required"))

        runBlocking {
            val actualResult = loginUserApiUc.run(params = paramsList)
            Assert.assertTrue((actualResult as? Either.Left<FailureBo>)?.let {
                it.a.msg == expectedResult.a.msg
            } ?: run {
                false
            })
        }
    }

}