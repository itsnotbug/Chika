package com.example.chika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chika.databinding.ActivitySigninBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            login(email,password)

        }
        binding.btnInRegister.setOnClickListener{
            val intent = Intent(this@SignIn,SignUp::class.java)
            startActivity(intent)
        }

    }

    private fun login(email: String, password: String) {
       mAuth.signInWithEmailAndPassword(email,password)
           .addOnCompleteListener {task->
               if(task.isSuccessful){
                   val intent = Intent(this@SignIn,MainActivity::class.java)
                   startActivity(intent)
               }else{
                   Toast.makeText(this@SignIn,"User does not exist",Toast.LENGTH_SHORT).show()
               }

           }
    }
}