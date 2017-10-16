package app.sample.diandroid.kotlinsampleapp.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import app.sample.diandroid.kotlinsampleapp.R
import app.sample.diandroid.kotlinsampleapp.app.BaseMvpActivity
import app.sample.diandroid.kotlinsampleapp.login.inject.DaggerLoginComponent
import app.sample.diandroid.kotlinsampleapp.login.inject.LoginModule
import app.sample.diandroid.kotlinsampleapp.login.presenter.LoginPresenter
import app.sample.diandroid.kotlinsampleapp.todoitems.view.ItemsListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : BaseMvpActivity<LoginView, LoginPresenter>() {

    override fun createPresenter(): LoginPresenter {
        return DaggerLoginComponent.builder().loginModule(LoginModule()).build().getLoginPresenter()
    }

    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mFirebaseAuth = FirebaseAuth.getInstance()

        signUpText.setOnClickListener {
            val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            var email = emailField.text.toString()
            var password = passwordField.text.toString()

            email = email.trim()
            password = password.trim()

            if (email.isEmpty() || password.isEmpty()) {
                val builder = AlertDialog.Builder(this@LogInActivity)
                builder.setMessage(R.string.login_error_message)
                        .setTitle(R.string.login_error_title)
                        .setPositiveButton(android.R.string.ok, null)
                val dialog = builder.create()
                dialog.show()
            } else {
                mFirebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this@LogInActivity) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this@LogInActivity, ItemsListActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            } else {
                                val builder = AlertDialog.Builder(this@LogInActivity)
                                builder.setMessage(task.exception!!.message)
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null)
                                val dialog = builder.create()
                                dialog.show()
                            }
                        }
            }
        }
    }
}
