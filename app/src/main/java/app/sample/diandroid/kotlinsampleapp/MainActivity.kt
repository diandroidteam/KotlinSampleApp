package app.sample.diandroid.kotlinsampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseUser: FirebaseUser? = null
    private var mDatabase: DatabaseReference? = null
    private var mUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth!!.currentUser
        mDatabase = FirebaseDatabase.getInstance().reference

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView()
        } else {
            mUserId = mFirebaseUser!!.uid

            // Set up ListView
            val listView = findViewById<ListView>(R.id.listView)
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1)
            listView.adapter = adapter

            // Add items via the Button and EditText at the bottom of the view.
            val text = findViewById<EditText>(R.id.todoText)
            val button = findViewById<Button>(R.id.addButton)
            button.setOnClickListener {
                val item = Item(text.text.toString())
                mDatabase!!.child("users").child(mUserId!!).child("items").push().setValue(item)
                text.setText("")
            }

            // Use Firebase to populate the list.
            mDatabase!!.child("users").child(mUserId!!).child("items").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {
                    adapter.add(dataSnapshot.child("title").value as String)
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {

                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    adapter.remove(dataSnapshot.child("title").value as String)
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

            // Delete items when clicked
            listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                mDatabase!!.child("users").child(mUserId!!).child("items")
                        .orderByChild("title")
                        .equalTo(listView.getItemAtPosition(position) as String)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    val firstChild = dataSnapshot.children.iterator().next()
                                    firstChild.ref.removeValue()
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {

                            }
                        })
            }
        }
    }

    private fun loadLogInView() {
        val intent = Intent(this, LogInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_logout) {
            mFirebaseAuth!!.signOut()
            loadLogInView()
        }

        return super.onOptionsItemSelected(item)
    }
}
