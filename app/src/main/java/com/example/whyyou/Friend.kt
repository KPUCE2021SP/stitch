package com.example.whyyou

import android.graphics.Insets.add
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friend.*
import kotlinx.android.synthetic.main.friend.view.*
import org.jetbrains.anko.startActivity

class Friend : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend)
        recView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val datas = mutableListOf<FriendData>()

        val friendAdapter : FriendAdapter = FriendAdapter(this)

        btn_sign_out.setOnClickListener {
            Firebase.auth.signOut()
            startActivity<LoginActivity>()
        }

        btnFriendAdd.setOnClickListener {
            startActivity<FriendRequest>()
        }

        val name = Firebase.auth.currentUser?.email

        datas.apply {
            name?.let { FriendData(it) }?.let { add(it) }

        }

        friendAdapter.replaceList(datas)
        recView.adapter = friendAdapter

//        btnFriendAdd.setOnClickListener {
//
//        }

    }
}