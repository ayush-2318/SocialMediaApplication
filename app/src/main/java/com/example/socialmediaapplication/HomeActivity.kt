package com.example.socialmediaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.menu_logout -> {
                //Toast.makeText(this, "logout clicked", Toast.LENGTH_SHORT).show()
                auth.signOut()
                val intent=Intent(this,LogInActivity::class.java)
                startActivity(intent)
                finish()

            }else->{
            val intent=Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()
            }

        }
        return  true
    }
    private fun init(){
        auth=Firebase.auth
    }


}