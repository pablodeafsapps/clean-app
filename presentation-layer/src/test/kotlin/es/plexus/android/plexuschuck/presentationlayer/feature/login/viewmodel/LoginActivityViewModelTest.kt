package es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel

import arrow.core.Either
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.R
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.domain.FailureVo
import es.plexus.android.plexuschuck.presentationlayer.domain.UserLoginVo
import es.plexus.android.plexuschuck.presentationlayer.feature.login.LoginContract
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LoginActivityViewModelTest {

    @InjectMocks
    private lateinit var viewModel: LoginActivityViewModel

    @Mock
    private lateinit var bridge: LoginDomainLayerBridge<UserLoginBo, Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `login successful with email and password correct`() {
        // Given
        val captor = argumentCaptor<(Either<FailureBo, Boolean>) -> Unit>()
        val userLogin =  UserLoginVo(
            email = "pablo.sordomartinez@plexus.es",
            password = "plexu5"
        )
        // When
        viewModel.onButtonSelected(LoginContract.Action.LOGIN, userLogin)
        // Then
        verify(bridge).loginUser(any(), any(), captor.capture())
        verifyNoMoreInteractions(bridge)
        captor.firstValue.invoke(Either.Right(true))

        Assert.assertEquals(LoginState.AccessGranted, getRenderState())

    }

    @Test
    fun `login error because email is not register`() {
        // Given
        val captor = argumentCaptor<(Either<FailureBo, Boolean>) -> Unit>()
        val userLogin =  UserLoginVo(
            email = "pablo.mudomartinez",
            password = "plexu5"
        )
        // When
        viewModel.onButtonSelected(LoginContract.Action.LOGIN, userLogin)
        // Then
        verify(bridge).loginUser(any(), any(), captor.capture())
        verifyNoMoreInteractions(bridge)
        captor.firstValue.invoke(Either.Right(false))

        val stateShowError = getRenderState() as? LoginState.ShowError
        Assert.assertEquals(FailureVo.Unknown(R.string.error_login_response).msgRes, stateShowError?.failure?.msgRes)

    }

    private fun getRenderState() =
        (viewModel.screenState.value as? ScreenState.Render<LoginState>)?.renderState
}