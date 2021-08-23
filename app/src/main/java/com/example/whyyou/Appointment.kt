package com.example.whyyou

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friend.*
import org.jetbrains.anko.toast

class Appointment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app)

        recView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val datas = mutableListOf<AppData>()
        val appAdapter : AppAdapter = AppAdapter(this)

        val currentUserEmail = Firebase.auth.currentUser?.email
        val db = Firebase.firestore
        val colRef = db.collection(currentUserEmail!!).document("App List").collection("App List").document()

        colRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("TAG", "data: ${snapshot.data}")
                toast(snapshot.data?.get("friend_name").toString())

                val appTitle = snapshot.data?.get("Title").toString()
                val friendName = snapshot.data?.get("friend_name").toString()
                val appDate = snapshot.data?.get("Date").toString()
                val appTime = snapshot.data?.get("Time").toString()

                datas.apply {
                    add(AppData(appTitle, friendName, appDate, appTime))
                }

                appAdapter.replaceList(datas)
                recView.adapter = appAdapter

            } else {
                Log.d("TAG", "data: null")
            }
        }
    }
}