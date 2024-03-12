package com.example.socialmediaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.socialmediaapplication.datamodel.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class LogInActivity : AppCompatActivity() {
    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignup:Button
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        // if user is login directly open home activity
        if(auth.currentUser!=null){
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            // destroy the activity
            finish()
        }
        btnLogin.setOnClickListener {
            val email=etEmail.text.toString()
            val password=etPassword.text.toString()
            logIn(email,password)
        }
        btnSignup.setOnClickListener {
            val email=etEmail.text.toString()
            val password=etPassword.text.toString()
            signUp(email,password)
        }
    }
    // function to initiliaze the view
    private fun init(){
        etEmail=findViewById(R.id.et_email)
        etPassword=findViewById(R.id.et_Password)
        btnLogin=findViewById(R.id.btn_login)
        btnSignup=findViewById(R.id.btn_signup)
        auth=Firebase.auth
    }
    // function to perform login
    private fun logIn(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    // destroy the activity
                    finish()
                } else {
                    Toast.makeText(this, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
    }
    // function to perform signup
    private fun signUp(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // add User data to firebase
                    val listOfFollowing= mutableListOf<String>()
                    listOfFollowing.add(" ")
                    val listOfTweets= mutableListOf<String>()
                    listOfTweets.add(" ")
                    val user=User(
                        userEmail = email,
                        userProfileImg = "",
                        listOfTweets = listOfTweets,
                        lisOfFollowing = listOfFollowing,
                        uid = auth.uid.toString()
                    )
                    addUserTodatabase(user)
                    val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    // destroy the activity
                    finish()
                } else {
                    Toast.makeText(this, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }
    }
    // Function to add user to database
    private fun addUserTodatabase(user:User){
        Firebase.database.getReference("user").child(user.uid).setValue(user)
    }

}