package com.example.chika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chika.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity,SignIn::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}