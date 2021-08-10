package com.example.whyyou

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friend_request.*
import org.jetbrains.anko.toast

@Suppress("NAME_SHADOWING")
class FriendRequest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_request)

        // 요청 버튼 눌렀을 때
        btn_request.setOnClickListener {
            toast("버튼 눌림")

            val firestore = Firebase.firestore
            val currentUser = Firebase.auth.uid
            val friendId = search_id.text.toString()

            firestore.collection("users")
                .whereEqualTo("id", friendId)
                .get()
                .addOnSuccessListener {
                    for(name in it!!.documents) {
                        Log.d("success", name["name"].toString())
                        toast(name["name"].toString())

                        firestore.collection("users")
                            .document(currentUser!!)
                            .get()
                            .addOnSuccessListener {
                                val currentUserId = it.get("id").toString()

                                val friendName = hashMapOf(
                                    "friend_name" to name["name"]
                                )

                                firestore.collection("friendList").document(currentUserId)
                                    .set(friendName)
                                    .addOnSuccessListener {
                                        toast("저장 성공")
                                    }
                                    .addOnFailureListener {
                                        toast("저장 실패")
                                    }
                            }

                        finish()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("fail", exception.toString())
                    toast("사용자 정보 없음")
                }


        }

    }
}