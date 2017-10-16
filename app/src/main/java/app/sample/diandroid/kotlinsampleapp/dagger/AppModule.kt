package app.sample.diandroid.kotlinsampleapp.dagger

import android.app.Application
import android.content.Context
import app.sample.diandroid.kotlinsampleapp.app.SampleApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app: SampleApplication) {

    @Provides
    @Singleton
    fun provideApplication() : Application = app

}
