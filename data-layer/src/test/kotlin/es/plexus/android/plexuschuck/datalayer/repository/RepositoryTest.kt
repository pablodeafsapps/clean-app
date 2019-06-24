package es.plexus.android.plexuschuck.datalayer.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.ICNDB_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.di.dataLayerModule
import es.plexus.android.plexuschuck.datalayer.domain.JokeDto
import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.ICNDB_REPOSITORY_TAG
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
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import retrofit2.Response

class RepositoryTest : KoinTest {

    private val icndbRepository: DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>>
            by inject(name = ICNDB_REPOSITORY_TAG)
    private lateinit var mockIcndbDataSource: DataLayerContract.IcndbDataSource

    @Before
    fun setUp() {
        // create data-source mock
        mockIcndbDataSource = mock {
            onBlocking { fetchIcndbJokesResponse(null) }.doReturn(getRetrofitSuccessfulResponse())
        }
        startKoin(
            listOf(
                dataLayerModule,
                module(override = true) { factory(name = ICNDB_DATA_SOURCE_TAG) { mockIcndbDataSource } })
        )
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
            val actualResult = icndbRepository.fetchJokes(params = paramsList)
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
            val actualResult = icndbRepository.fetchJokes(params = paramsList)
            Assert.assertTrue(actualResult as? Either.Right<List<JokeBo>> != null)
        }
    }

    private fun getRetrofitSuccessfulResponse(): Response<JokeDtoWrapper> {
        val mockJokeDto = JokeDto(id = 1, joke = "not funny!", categories = listOf())
        val mockJokeDtoWrapper = JokeDtoWrapper(type = "mock", value = listOf(mockJokeDto))
        return Response.success(mockJokeDtoWrapper)
    }

}