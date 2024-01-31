package com.example.chika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chika.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recycleView:RecyclerView
    private lateinit var userList: MutableList<User>
    private lateinit var  adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userList = mutableListOf()
        adapter = UserAdapter(this,userList)
        recycleView = binding.rVUserList
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        checkCurrentUser()
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //user list is a must due to onDataChange setup
                userList.clear()
               for(postSnapshot in snapshot.children)
               {
                   val currentUser =postSnapshot.getValue(User::class.java)
                   if(mAuth.currentUser?.uid!=currentUser?.uid){
                       userList.add(currentUser!!)
                   }
               }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            Log.d("Cancelled", "Error: ${error.message}" )
            }

        })

        binding.materialToolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.logout->{
                    mAuth.signOut()
                    val intent = Intent(this@MainActivity,SignIn::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

    private fun checkCurrentUser() {
        val user = mAuth.currentUser
        if(user==null){
            val intent = Intent(this@MainActivity,SignIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}