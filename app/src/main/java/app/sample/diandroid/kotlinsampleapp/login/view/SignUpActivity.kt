package app.sample.diandroid.kotlinsampleapp.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import app.sample.diandroid.kotlinsampleapp.R
import app.sample.diandroid.kotlinsampleapp.todoitems.view.ItemsListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseAuth = FirebaseAuth.getInstance()

        signupButton.setOnClickListener {
            var password = passwordField.text.toString()
            var email = emailField.text.toString()

            password = password.trim()
            email = email.trim()

            if (password.isEmpty() || email.isEmpty()) {
                val builder = AlertDialog.Builder(this@SignUpActivity)
                builder.setMessage(R.string.signup_error_message)
                        .setTitle(R.string.signup_error_title)
                        .setPositiveButton(android.R.string.ok, null)
                val dialog = builder.create()
                dialog.show()
            } else {
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this@SignUpActivity) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this@SignUpActivity, ItemsListActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            } else {
                                val builder = AlertDialog.Builder(this@SignUpActivity)
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
