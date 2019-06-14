package es.plexus.android.plexuschuck.presentationlayer.feature.login.view.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer

import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.presentationlayer.R
import es.plexus.android.plexuschuck.presentationlayer.base.BaseMvvmActivity
import es.plexus.android.plexuschuck.presentationlayer.base.ScreenState
import es.plexus.android.plexuschuck.presentationlayer.feature.login.LoginContract.Action
import es.plexus.android.plexuschuck.presentationlayer.feature.login.view.state.LoginState
import es.plexus.android.plexuschuck.presentationlayer.feature.login.viewmodel.LoginActivityViewModel

import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseMvvmActivity<LoginActivityViewModel, LoginDomainLayerBridge<List<String?>, Boolean>, LoginState>() {

//    private val loginViewModel: LoginActivityViewModel? by lazy { getViewModelInstance() }
    private val loginViewModel: LoginActivityViewModel? by viewModel()
    private val pbLoading: ProgressBar? by lazy { activity_login__pb__loading }
    private val tvTitle: TextView? by lazy { activity_login_tv_title }
    private val etEmail: EditText? by lazy { activity_login__et__email }
    private val etPassword: EditText? by lazy { activity_login__et__password }
    private val btnLogin: Button? by lazy { activity_login__btn__login }
    private val btnRegister: Button? by lazy { activity_login__btn__register }
    private val tbAccessMode: ToggleButton? by lazy { activity_login__tb__access_mode }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initModel()
        initView()
    }

    private fun initModel() {
        loginViewModel?.screenState?.observe(this, Observer { screenState ->
            when (screenState) {
                is ScreenState.Render<LoginState> -> processRenderState(screenState.renderState)
                is ScreenState.Loading -> showLoading()
            }
        })
    }

    private fun initView() {
        btnLogin?.setOnClickListener {
            // correct login: pablo@mytest.com, pablomytest
            loginViewModel?.onButtonClicked(
                Action.LOGIN, etEmail?.text?.toString(), etPassword?.text?.toString()
            )
        }
        btnRegister?.setOnClickListener {
            loginViewModel?.onButtonClicked(
                Action.REGISTER, etEmail?.text?.toString(), etPassword?.text?.toString()
            )
        }
        tbAccessMode?.setOnClickListener {
            loginViewModel?.onToggleModeTapped(btnLogin?.visibility == View.VISIBLE)
        }
    }

    override fun processRenderState(renderState: LoginState?) {
        when (renderState) {
            is LoginState.Idle -> hideLoading()
            is LoginState.Loading -> showLoading()
            is LoginState.Login -> showLoginUi()
            is LoginState.Register -> showRegisterUi()
            is LoginState.AccessGranted -> navigateToMainActivity()
        }
    }

    private fun showLoginUi() {
        tvTitle?.text = this.getString(R.string.tv_login_text)
        btnLogin?.visibility = View.VISIBLE
        btnRegister?.visibility = View.INVISIBLE
    }

    private fun showRegisterUi() {
        tvTitle?.text = this.getString(R.string.tv_register_text)
        btnLogin?.visibility = View.INVISIBLE
        btnRegister?.visibility = View.VISIBLE
    }

    private fun showLoading() {
        pbLoading?.visibility = View.VISIBLE
        etEmail?.isEnabled = false
        etPassword?.isEnabled = false
        btnLogin?.isEnabled = false
        tbAccessMode?.isEnabled = false
        btnRegister?.isEnabled = false
    }

    private fun hideLoading() {
        pbLoading?.visibility = View.GONE
        etEmail?.isEnabled = true
        etPassword?.isEnabled = true
        btnLogin?.isEnabled = true
        tbAccessMode?.isEnabled = true
        btnRegister?.isEnabled = true
    }

    private fun navigateToMainActivity() {
        // TODO: implement navigation
    }

}