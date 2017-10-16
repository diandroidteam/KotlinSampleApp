package app.sample.diandroid.kotlinsampleapp.app

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter

/**
 * Created by navas on 16/10/17.
 */
open class BaseMvpPresenter<V : BaseMvpView> : MvpBasePresenter<V>() {

}