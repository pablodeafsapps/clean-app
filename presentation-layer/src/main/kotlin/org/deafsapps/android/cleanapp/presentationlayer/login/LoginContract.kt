package org.deafsapps.android.cleanapp.presentationlayer.login

import org.deafsapps.android.cleanapp.presentationlayer.base.MvpPresenter
import org.deafsapps.android.cleanapp.presentationlayer.base.MvpView

interface LoginContract {

    enum class Action {
        LOGIN, REGISTER
    }

    interface View : MvpView {
        fun showLoginUi()
        fun showRegisterUi()
        fun showLoading()
        fun hideLoading()
        fun showInfoMessage(msg: String)
        fun clearTextFields()
    }

    interface Presenter : MvpPresenter<View> {
        fun onButtonClicked(action: Action, email: String?, password: String?)
        fun onToggleModeTapped(isLoginMode: Boolean)
    }

}