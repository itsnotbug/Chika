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
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth.currentUser
//        if (currentUser != null) {
//            val intent = Intent(this@SignIn,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        //lottie binding and animation
        binding.lAChickenIcon.playAnimation()

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            if(email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this@SignIn,"Fields are empty",Toast.LENGTH_SHORT).show()
            }else{
                //direct checking from firebase and login-in
                login(email,password)
            }


        }
        binding.txtViewSignUp.setOnClickListener{
            val intent = Intent(this@SignIn,SignUp::class.java)
            startActivity(intent)
            finish()
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