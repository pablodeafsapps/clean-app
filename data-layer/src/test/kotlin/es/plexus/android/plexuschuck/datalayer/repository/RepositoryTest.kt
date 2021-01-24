package es.plexus.android.plexuschuck.datalayer.repository

import arrow.core.Either
import arrow.core.right
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.plexus.android.plexuschuck.datalayer.datasource.AuthenticationDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.AuthenticationDataSource.Companion.AUTHENTICATION_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.ConnectivityDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.ConnectivityDataSource.Companion.CONNECTIVITY_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.JokesDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.JokesDataSource.Companion.JOKES_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.di.dataLayerModule
import es.plexus.android.plexuschuck.datalayer.domain.JokeDto
import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import es.plexus.android.plexuschuck.datalayer.domain.dtoToBo
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.DATA_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBoWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

@ExperimentalCoroutinesApi
class RepositoryTest : KoinTest {

    private val dataRepository: DomainlayerContract.Datalayer.DataRepository<JokeBoWrapper>
            by inject(named(name = DATA_REPOSITORY_TAG))
    private lateinit var mockConnectivityDataSource: ConnectivityDataSource
    private lateinit var mockAuthenticationDataSource: AuthenticationDataSource
    private lateinit var mockJokesDataSource: JokesDataSource

    @Before
    fun setUp() {
        // create data-source mock
        mockConnectivityDataSource = mock()
        mockAuthenticationDataSource = mock()
        mockJokesDataSource = mock()
        startKoin {
            modules(listOf(
                dataLayerModule,
                module(override = true) {
                    factory(named(name = CONNECTIVITY_DATA_SOURCE_TAG)) { mockConnectivityDataSource }
                    factory(named(name = AUTHENTICATION_DATA_SOURCE_TAG)) { mockAuthenticationDataSource }
                    factory(named(name = JOKES_DATA_SOURCE_TAG)) { mockJokesDataSource }
                }
            ))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check that if data-source response is successful, a List of JokeBo objects is returned`() =
        runBlockingTest {
            // given
            whenever(mockConnectivityDataSource.checkNetworkConnectionAvailability()).doReturn(true)
            whenever(mockJokesDataSource.fetchJokesResponse()).doReturn(getDummyJokeBoWrapper().right())
            // when
            val actualResult = dataRepository.fetchJokes()
            // then
            Assert.assertTrue((actualResult as? Either.Right<JokeBoWrapper>) != null)
        }

    private fun getDummyJokeBoWrapper() = JokeDtoWrapper(
        type = "mock",
        value = listOf(JokeDto(id = 1, joke = "not funny!", categories = listOf()))
    ).dtoToBo()

}
