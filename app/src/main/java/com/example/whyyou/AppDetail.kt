package com.example.whyyou

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_datail.*
import kotlinx.android.synthetic.main.friend_app_add.friendname

class AppDetail : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.app_datail)

        appdtitle.text = intent.getStringExtra("app_title")
        appdtime.text = intent.getStringExtra("app_date") + " " + intent.getStringExtra("app_time")
        appdwith.text = "with " + intent.getStringExtra("friend_name")
        appdlocation.text = "장소 : " + intent.getStringExtra("app_location")

    }
}