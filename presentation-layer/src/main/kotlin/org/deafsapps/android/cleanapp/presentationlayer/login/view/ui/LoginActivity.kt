package org.deafsapps.android.cleanapp.presentationlayer.login.view.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.deafsapps.android.cleanapp.presentationlayer.MainActivity
import org.deafsapps.android.cleanapp.presentationlayer.R
import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract
import org.deafsapps.android.cleanapp.presentationlayer.login.LoginContract.Action
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

private const val EMPTY_STRING = ""

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private val loginPresenter: LoginContract.Presenter? by inject { parametersOf(this) }
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

        btnLogin?.setOnClickListener {
            // correct login: pablo@mytest.com, pablomytest
            loginPresenter?.onButtonClicked(
                Action.LOGIN,
                etEmail?.text?.toString(),
                etPassword?.text?.toString()
            )
        }
        btnRegister?.setOnClickListener {
            loginPresenter?.onButtonClicked(
                Action.REGISTER,
                etEmail?.text?.toString(),
                etPassword?.text?.toString()
            )
        }
        tbAccessMode?.setOnClickListener {
            loginPresenter?.onToggleModeTapped(btnLogin?.visibility == View.VISIBLE)
        }

    }

    override fun onResume() {
        super.onResume()
        loginPresenter?.onViewResumed()
    }

    override fun showLoginUi() {
        tvTitle?.text = this.getString(R.string.tv_login_text)
        btnLogin?.visibility = View.VISIBLE
        btnRegister?.visibility = View.INVISIBLE
    }

    override fun showRegisterUi() {
        tvTitle?.text = this.getString(R.string.tv_register_text)
        btnLogin?.visibility = View.INVISIBLE
        btnRegister?.visibility = View.VISIBLE
    }

    override fun showLoading() {
        pbLoading?.visibility = View.VISIBLE
        etEmail?.isEnabled = false
        etPassword?.isEnabled = false
        btnLogin?.isEnabled = false
        tbAccessMode?.isEnabled = false
        btnRegister?.isEnabled = false
    }

    override fun hideLoading() {
        pbLoading?.visibility = View.GONE
        etEmail?.isEnabled = true
        etPassword?.isEnabled = true
        btnLogin?.isEnabled = true
        tbAccessMode?.isEnabled = true
        btnRegister?.isEnabled = true
    }

    override fun showInfoMessage(msg: String) {
        toast(msg)
    }

    override fun clearTextFields() {
        etPassword?.setText(EMPTY_STRING)
    }

    override fun navigateToMainActivity() {
        startActivity<MainActivity>()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter?.onDetach()
    }

}