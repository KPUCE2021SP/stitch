package com.example.friend_app_add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_app_add.setOnClickListener{
            startActivity<FriendAppAdd>()
        }
        btn_applist.setOnClickListener {
            startActivity<Appointment>()
        }
    }
}