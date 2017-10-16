package app.sample.diandroid.kotlinsampleapp.app

import android.app.Application
import app.sample.diandroid.kotlinsampleapp.dagger.AppComponent
import app.sample.diandroid.kotlinsampleapp.dagger.AppModule
import app.sample.diandroid.kotlinsampleapp.dagger.DaggerAppComponent

/**
 * Created by navas on 08/10/17.
 */
class SampleApplication : Application(){

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }


}