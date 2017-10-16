package app.sample.diandroid.kotlinsampleapp.login.inject

import app.sample.diandroid.kotlinsampleapp.dagger.AppModule
import app.sample.diandroid.kotlinsampleapp.login.presenter.LoginPresenter
import app.sample.diandroid.kotlinsampleapp.login.view.LogInActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, LoginModule::class))
interface LoginComponent {
    fun inject(logInActivity: LogInActivity)
    fun getLoginPresenter(): LoginPresenter
}