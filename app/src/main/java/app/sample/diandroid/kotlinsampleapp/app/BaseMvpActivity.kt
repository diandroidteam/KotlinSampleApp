package app.sample.diandroid.kotlinsampleapp.app

import app.sample.diandroid.kotlinsampleapp.dagger.AppComponent
import com.hannesdorfmann.mosby3.mvp.MvpActivity

abstract class BaseMvpActivity<V : BaseMvpView, P : BaseMvpPresenter<V>> : MvpActivity<V, P>(), BaseMvpView {

    override fun showProgressDialog() {
    }

    override fun hideProgressDialog() {
    }

    fun getSampleApplication(): SampleApplication {
        return application as SampleApplication
    }

    fun getAppComponent(): AppComponent {
        return SampleApplication.appComponent
    }
}