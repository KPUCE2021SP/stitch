package com.example.whyyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Firebase.auth.currentUser == null) {
            startActivity<LoginActivity>()
            finish()
        }

        startActivity<Friend>()

//        btn_sign_out.setOnClickListener {
//            Firebase.auth.signOut()
//            startActivity<LoginActivity>()
//        }
    }
}