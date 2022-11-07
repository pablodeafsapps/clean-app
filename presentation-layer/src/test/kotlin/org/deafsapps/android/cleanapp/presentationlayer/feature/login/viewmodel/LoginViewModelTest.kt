package org.deafsapps.android.cleanapp.presentationlayer.feature.login.viewmodel

import arrow.core.Either
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LOGIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.UserLoginVo
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.LoginContract
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.navigator.LoginNavigator
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.view.state.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
class LoginViewModelTest : KoinTest {

    private val viewModel: LoginViewModel by inject()
    private lateinit var mockBridge: LoginDomainLayerBridge<UserLoginBo>
    private lateinit var mockNavigator: LoginNavigator

    @Before
    fun setUp() {
        mockBridge = mock()
        mockNavigator = mock()

        startKoin {
            modules(
                listOf(
                    module(override = true) {
                        factory { mockBridge }
                        factory { mockNavigator }
                        factory { LoginViewModel(get(), get()) }
                    }
                ))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check that login is successful with email and password correct`() {
        // given
        val rightEmail = "example@cleanapp.es"
        val rightPassword = "password"
        val captor = argumentCaptor<(Either<FailureBo, Boolean>) -> Unit>()
        val userLogin = UserLoginVo(email = rightEmail, password = rightPassword)
        // when
        viewModel.onButtonSelected(LoginContract.Action.LOGIN, userLogin)
        // then
        verify(mockBridge).loginUser(any(), any(), captor.capture())
        verifyNoMoreInteractions(mockBridge)
        captor.firstValue.invoke(true.right())

        Assert.assertEquals(LoginState.AccessGranted, getRenderState())
    }

    @Test
    fun `check that login is error because email is not register`() {
        // given
        val wrongEmail = "emailNotRegister@cleanapp.es"
        val wrongPassword = "password"
        val captor = argumentCaptor<(Either<FailureBo, Boolean>) -> Unit>()
        val userLogin = UserLoginVo(email = wrongEmail, password = wrongPassword)
        // when
        viewModel.onButtonSelected(LoginContract.Action.LOGIN, userLogin)
        // then
        verify(mockBridge).loginUser(any(), any(), captor.capture())
        verifyNoMoreInteractions(mockBridge)
        captor.firstValue.invoke(false.right())

        val stateShowError = getRenderState() as? LoginState.ShowError
        Assert.assertEquals(FailureVo.Unknown.msg, stateShowError?.failure?.msg)
    }

    private fun getRenderState() =
        (viewModel.screenState.value as? ScreenState.Render<LoginState>)?.renderState
}
