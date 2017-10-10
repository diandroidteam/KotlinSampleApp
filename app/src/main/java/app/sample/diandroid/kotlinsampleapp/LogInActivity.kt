package app.sample.diandroid.kotlinsampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    protected lateinit var emailEditText: EditText
    protected lateinit var passwordEditText: EditText
    protected lateinit var logInButton: Button
    protected lateinit var signUpTextView: TextView
    private var mFirebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance()

        signUpTextView = findViewById<View>(R.id.signUpText) as TextView
        emailEditText = findViewById<View>(R.id.emailField) as EditText
        passwordEditText = findViewById<View>(R.id.passwordField) as EditText
        logInButton = findViewById<View>(R.id.loginButton) as Button

        signUpTextView.setOnClickListener {
            val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        logInButton.setOnClickListener {
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()

            email = email.trim { it <= ' ' }
            password = password.trim { it <= ' ' }

            if (email.isEmpty() || password.isEmpty()) {
                val builder = AlertDialog.Builder(this@LogInActivity)
                builder.setMessage(R.string.login_error_message)
                        .setTitle(R.string.login_error_title)
                        .setPositiveButton(android.R.string.ok, null)
                val dialog = builder.create()
                dialog.show()
            } else {
                mFirebaseAuth!!.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this@LogInActivity) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this@LogInActivity, MainActivity::class.java)
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
