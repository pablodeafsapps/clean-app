package es.plexus.android.plexuschuck.datalayer.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.JOKES_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.di.dataLayerModule
import es.plexus.android.plexuschuck.datalayer.domain.JokeDto
import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.DATA_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Response

class RepositoryTest : KoinTest {

    private val dataRepository: DomainlayerContract.Datalayer.DataRepository<List<String>?, List<JokeBo>>
            by inject(named(name = DATA_REPOSITORY_TAG))
    private lateinit var mockIcndbDataSource: DataLayerContract.JokesDataSource

    @Before
    fun setUp() {
        // create data-source mock
        mockIcndbDataSource = mock {
            onBlocking { fetchJokesResponse(null) }.doReturn(getRetrofitSuccessfulResponse())
        }
        startKoin {
            modules(
                listOf(
                    dataLayerModule,
                    module(override = true) { factory(named(name = JOKES_DATA_SOURCE_TAG)) { mockIcndbDataSource } }
                )
            )
        }
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `check that if data-source response is successful, a non-empty joke list is returned`() {
        val paramsList = null

        runBlocking {
            val actualResult = dataRepository.fetchJokes(params = paramsList)
            Assert.assertTrue((actualResult as? Either.Right<List<JokeBo>>)?.let {
                println("list size: ${it.b.size}")
                it.b.isNotEmpty()
            } ?: run {
                false
            })
        }
    }

    @Test
    fun `check that if data-source response is successful, a List of JokeBo objects is returned`() {
        val paramsList = null

        runBlocking {
            val actualResult = dataRepository.fetchJokes(params = paramsList)
            Assert.assertTrue(actualResult as? Either.Right<List<JokeBo>> != null)
        }
    }

    private fun getRetrofitSuccessfulResponse(): Response<JokeDtoWrapper> {
        val mockJokeDto = JokeDto(id = 1, joke = "not funny!", categories = listOf())
        val mockJokeDtoWrapper = JokeDtoWrapper(type = "mock", value = listOf(mockJokeDto))
        return Response.success(mockJokeDtoWrapper)
    }

}