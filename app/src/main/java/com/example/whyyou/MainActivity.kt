package com.example.whyyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var after_login_viewpager = findViewById<ViewPager>(R.id.dlg_viewpager)
        var after_login_tablayout = findViewById<TabLayout>(R.id.dlg_tablayout)

        val adapter = ViewpagerAdapter(supportFragmentManager)
        adapter.addFragment(Friend(), "친구")
        adapter.addFragment(Appointment(), "약속")
        adapter.addFragment(History(), "기록")
        after_login_viewpager.adapter = adapter
        after_login_tablayout.setupWithViewPager(after_login_viewpager)

    }
}