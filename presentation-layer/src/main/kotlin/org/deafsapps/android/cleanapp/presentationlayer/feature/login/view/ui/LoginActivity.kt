package org.deafsapps.android.cleanapp.presentationlayer.feature.login.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.presentationlayer.R
import org.deafsapps.android.cleanapp.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.cleanapp.presentationlayer.base.ScreenState
import org.deafsapps.android.cleanapp.presentationlayer.databinding.ActivityLoginBinding
import org.deafsapps.android.cleanapp.presentationlayer.domain.FailureVo
import org.deafsapps.android.cleanapp.presentationlayer.domain.UserLoginVo
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.LoginContract.Action
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.navigator.LoginNavigator
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.view.state.LoginState
import org.deafsapps.android.cleanapp.presentationlayer.feature.login.viewmodel.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import org.koin.android.scope.ScopeActivity
import org.koin.android.viewmodel.ext.android.viewModel

private const val EMPTY_STRING = ""

/**
 * This [AppCompatActivity] represents the login feature of the application. It is here where the
 * user can procceed to log-in or register his account.
 *
 * The UI state is controlled thanks to the collection of a [viewModel] observable variable.
 */
@ExperimentalCoroutinesApi
class LoginActivity : ScopeActivity(),
    BaseMvvmView<LoginViewModel, LoginDomainLayerBridge<UserLoginBo>, LoginNavigator, LoginState> {

    override val viewModel: LoginViewModel by viewModel()
    private lateinit var viewBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        initModel()
        initView()
        setContentView(viewBinding.root)
        viewModel.onViewCreated()
    }

    override fun processRenderState(renderState: LoginState) {
        when (renderState) {
            is LoginState.Login -> showLoginUi()
            is LoginState.Register -> showRegisterUi()
            is LoginState.AccessGranted -> viewModel.navigateToMainActivity()
            is LoginState.ShowError -> showError(failure = renderState.failure)
        }
    }

    override fun initModel() {
        lifecycleScope.launch {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Idle -> hideLoading()
                    is ScreenState.Loading -> showLoading()
                    is ScreenState.Render<LoginState> -> {
                        processRenderState(screenState.renderState)
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun initView() {
        with(viewBinding) {
            root.setOnClickListener {
                viewModel.onRootSelected()
            }
            root.setOnLongClickListener {
                viewModel.onRootLongSelected()
                true
            }
            btnLogin.setOnClickListener {
                // correct login: pablo@mytest.com, pablomytest
                viewModel.onButtonSelected(
                    action = Action.LOGIN,
                    userData = UserLoginVo(
                        email = etEmail.text?.toString(), password = etPassword.text?.toString()
                    )
                )
            }
            btnRegister.setOnClickListener {
                viewModel.onButtonSelected(
                    action = Action.REGISTER,
                    userData = UserLoginVo(
                        email = etEmail.text?.toString(), password = etPassword.text?.toString()
                    )
                )
            }
            tbAccessMode.setOnClickListener {
                viewModel.onToggleModeTapped(btnLogin.visibility == View.VISIBLE)
            }
        }
    }

    private fun showLoginUi() {
        with(viewBinding) {
            tvTitle.text = getString(R.string.tv_login_text)
            btnLogin.visibility = View.VISIBLE
            btnRegister.visibility = View.INVISIBLE
        }
    }

    private fun showRegisterUi() {
        with(viewBinding) {
            tvTitle.text = getString(R.string.tv_register_text)
            btnLogin.visibility = View.INVISIBLE
            btnRegister.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.VISIBLE
            etEmail.isEnabled = false
            etPassword.isEnabled = false
            btnLogin.isEnabled = false
            tbAccessMode.isEnabled = false
            btnRegister.isEnabled = false
        }
    }

    private fun hideLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.GONE
            etEmail.isEnabled = true
            etPassword.isEnabled = true
            etPassword.setText(EMPTY_STRING)
            btnLogin.isEnabled = true
            tbAccessMode.isEnabled = true
            btnRegister.isEnabled = true
        }
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.getErrorMessage() != null) {
            toast(failure.getErrorMessage())
        }
    }

}
