package com.example.whyyou

import android.graphics.Insets.add
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friend.*
import kotlinx.android.synthetic.main.friend.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Friend : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend)
        recView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val datas = mutableListOf<FriendData>()

        val friendAdapter : FriendAdapter = FriendAdapter(this)

        // 로그아웃 버튼 눌렀을 때 로그인 화면으로 이동
        btn_sign_out.setOnClickListener {
            Firebase.auth.signOut()
            startActivity<LoginActivity>()
        }

        // 친구추가 버튼 눌렀을 때 친구 요청 화면으로 이동
        btnFriendAdd.setOnClickListener {
            startActivity<FriendRequest>()
        }

        val currentUserEmail = Firebase.auth.currentUser?.email

        val db = Firebase.firestore
        val docRef = db.collection(currentUserEmail!!).document("Friend List")

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val d = Log.d("TAG", "data: ${snapshot.data}")
                toast(snapshot.data?.get("friend_name").toString())

                val friendList = snapshot.data?.get("friend_name") as ArrayList<*>
                val listSize = friendList.size

                datas.clear()
                for (i in 0 until listSize) {
                    datas.apply {
                        add(FriendData(friendList[i] as String))
                    }
                }

                friendAdapter.replaceList(datas)
                recView.adapter = friendAdapter

            } else {
                Log.d("TAG", "data: null")
            }
        }
    }
}