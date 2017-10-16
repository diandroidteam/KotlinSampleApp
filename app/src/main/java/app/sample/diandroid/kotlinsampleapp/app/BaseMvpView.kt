package app.sample.diandroid.kotlinsampleapp.app

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by navas on 16/10/17.
 */
interface BaseMvpView : MvpView{

    fun showProgressDialog()

    fun hideProgressDialog()

}