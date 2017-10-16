package app.sample.diandroid.kotlinsampleapp.todoitems.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import app.sample.diandroid.kotlinsampleapp.todoitems.bean.Item
import app.sample.diandroid.kotlinsampleapp.R
import app.sample.diandroid.kotlinsampleapp.login.view.LogInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class ItemsListActivity : AppCompatActivity() {

    private lateinit var mFirebaseAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser? = null
    private lateinit var mDatabase: DatabaseReference
    private var mUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth.currentUser
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

            addButton.setOnClickListener {
                val item = Item(todoText.text.toString())
                mDatabase.child("items").push().setValue(item)
                todoText.setText("")
            }

            // Use Firebase to populate the list.
            mDatabase.child("items").addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                }

                override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                    adapter.add(dataSnapshot?.child("title")?.value as String)
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                    adapter.remove(dataSnapshot?.child("title")?.value as String)
                }

            })

            // Delete items when clicked
            listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                mDatabase.child("items")
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
            mFirebaseAuth.signOut()
            loadLogInView()
        }

        return super.onOptionsItemSelected(item)
    }
}
