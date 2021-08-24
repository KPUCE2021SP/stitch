package com.example.whyyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.api.ResourceDescriptor
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

//        startActivity<Friend>()

        val after_login_viewpager = findViewById<ViewPager>(R.id.dlg_date_viewpager)
        val after_login_tablayout = findViewById<TabLayout>(R.id.dlg_date_tablayout)

        val adapter = ViewpagerAdapter(supportFragmentManager)
        adapter.addFragment(Friend(), "Friend")
        adapter.addFragment(Appointment(), "Appointment")
//        adapter.addFragment(ResourceDescriptor.History(), "Emo")
        after_login_viewpager.adapter = adapter
        after_login_tablayout.setupWithViewPager(after_login_viewpager)

//        btn_sign_out.setOnClickListener {
//            Firebase.auth.signOut()
//            startActivity<LoginActivity>()
//        }
    }
}