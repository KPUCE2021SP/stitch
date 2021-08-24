package com.example.whyyou

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friend.*
import org.jetbrains.anko.toast

@Suppress("NAME_SHADOWING")
class Appointment : Fragment() {

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.app, null).apply {
            recView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val datas = mutableListOf<AppData>()
            val appAdapter : AppAdapter = AppAdapter(context)

            val currentUserEmail = Firebase.auth.currentUser?.email
            val db = Firebase.firestore
            val colRef = db.collection(currentUserEmail!!).document("App List").collection("App List")

            colRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    return@addSnapshotListener
                }

                datas.clear()
                for (snapshot in snapshot!!) {
                    val appTitle = snapshot.data["Title"].toString()
                    val friendName = snapshot.data["friend_name"].toString()
                    val appDate = snapshot.data["Date"].toString()
                    val appTime = snapshot.data["Time"].toString()

                    datas.apply {
                        add(AppData(appTitle, friendName, appDate, appTime))
                    }

                    appAdapter.replaceList(datas)
                    recView.adapter = appAdapter
                }
            }
        }
    }
}