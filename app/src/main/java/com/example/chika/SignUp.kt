package com.example.chika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chika.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val name = binding.edTxtUsername.text.toString()
            val email = binding.edTxtEmail.text.toString()
            val password = binding.edTxtPassword.text.toString()
            signUp(name,email,password)
        }

    }

    private fun signUp(name:String,email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task->
                 if(task.isSuccessful){
                     //add user to real time database
                     addUserToDatabase(name,email,mAuth.uid!!)
                     //proceed to the  sign in
                   val intent = Intent(this@SignUp,SignIn::class.java)
                    startActivity(intent)
                }else{
                   Toast.makeText(this,"Some error occurred",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbref = FirebaseDatabase.getInstance().reference
        mDbref.child("user").child(uid).setValue(User(name,email,uid))
    }
}