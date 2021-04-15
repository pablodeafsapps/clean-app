package es.plexus.android.plexuschuck.datalayer.repository

import arrow.core.Either
import arrow.core.left
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
import es.plexus.android.plexuschuck.datalayer.datasource.SessionDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.SessionDataSource.Companion.SESSION_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.di.dataLayerModule
import es.plexus.android.plexuschuck.datalayer.domain.*
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.AUTHENTICATION_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.domain.ErrorMessage
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo
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
class LoginTest : KoinTest {

    private val dataRepository: DomainlayerContract.Datalayer.AuthenticationRepository<UserLoginBo, Boolean>
            by inject(named(name = AUTHENTICATION_REPOSITORY_TAG))
    private lateinit var mockConnectivityDataSource: ConnectivityDataSource
    private lateinit var mockAuthenticationDataSource: AuthenticationDataSource
    private lateinit var mockJokesDataSource: JokesDataSource
    private lateinit var mockSessionDataSource: SessionDataSource

    @Before
    fun setUp() {
        // create data-source mock
        mockConnectivityDataSource = mock()
        mockAuthenticationDataSource = mock()
        mockJokesDataSource = mock()
        mockSessionDataSource = mock()
        startKoin {
            modules(listOf(
                dataLayerModule,
                module(override = true) {
                    factory(named(name = CONNECTIVITY_DATA_SOURCE_TAG)) { mockConnectivityDataSource }
                    factory(named(name = AUTHENTICATION_DATA_SOURCE_TAG)) { mockAuthenticationDataSource }
                    factory(named(name = JOKES_DATA_SOURCE_TAG)) { mockJokesDataSource }
                    factory(named(name = SESSION_DATA_SOURCE_TAG)) { mockSessionDataSource }
                }
            ))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check that if a login and try to save data in persistence fails`() =
        runBlockingTest {
            // given
            whenever(mockConnectivityDataSource.checkNetworkConnectionAvailability()).doReturn(true)
            whenever(mockAuthenticationDataSource.requestLogin(getDummyLoginUserDto())).doReturn(
                getDummyUserSessionDto().right()
            )
            whenever(mockSessionDataSource.saveUserSession(getDummyUserSessionDto())).doReturn(
                FailureDto.Error(ErrorMessage.ERROR_GET_SESSION_SHARED_PREFERENCES).left()
            )
            // when
            val result = dataRepository.loginUser(UserLoginBo("", ""))
            // then
            Assert.assertTrue((result as? Either.Left<FailureBo>) != null)
        }

    @Test
    fun `check that if a login and save data in persistence is successfully`() =
        runBlockingTest {
            // given
            whenever(mockConnectivityDataSource.checkNetworkConnectionAvailability()).doReturn(true)
            whenever(mockAuthenticationDataSource.requestLogin(getDummyLoginUserDto())).doReturn(
                getDummyUserSessionDto().right()
            )
            whenever(mockSessionDataSource.saveUserSession(getDummyUserSessionDto())).doReturn(
                true.right()
            )
            // when
            val result = dataRepository.loginUser(UserLoginBo("", ""))
            // then
            Assert.assertTrue((result as? Either.Right<Boolean>) != null)
        }

    private fun getDummyLoginUserDto() = UserLoginDto(
        email = "",
        password = ""
    )

    private fun getDummyUserSessionDto() = UserSessionDto(
        email = "",
        uuid = "",
        name = ""
    )

}
