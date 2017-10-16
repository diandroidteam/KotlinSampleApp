package app.sample.diandroid.kotlinsampleapp.login.inject

import app.sample.diandroid.kotlinsampleapp.login.presenter.LoginPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginModule {

    @Provides
    @Singleton
    fun providesLoginPresenter(): LoginPresenter {
        return LoginPresenter()
    }

}