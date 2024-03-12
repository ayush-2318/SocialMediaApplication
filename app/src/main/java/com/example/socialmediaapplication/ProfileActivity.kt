package com.example.socialmediaapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.net.URI
import java.util.UUID

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileImg:CircleImageView
    private lateinit var btnOpenGallery:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        btnOpenGallery.setOnClickListener {
            val galleryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,101)
        }
    }
    private fun init(){
        profileImg=findViewById(R.id.profile_image)
        btnOpenGallery=findViewById(R.id.btn_open_gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (resultCode== RESULT_OK &&  resultCode==101){
           profileImg.setImageURI(data?.data)
           uploadImage(data?.data)
       }
    }
    private fun uploadImage(uri:Uri?){
        val profileImageName=UUID.randomUUID().toString()+".jpg"
        val storageref=FirebaseStorage.getInstance().getReference().child("profileImage$profileImageName")
        storageref.putFile(uri!!).addOnSuccessListener {
            val result=it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                FirebaseDatabase.getInstance().reference.child("user").child(Firebase.auth.uid.toString())
                    .child("userProfileImg").setValue(it.toString())
            }
        }

    }
}