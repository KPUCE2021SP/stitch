package com.example.whyyou

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
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

            val firestore = Firebase.firestore
            val currentUser = Firebase.auth.uid   // 현재 사용자 uid (계정 구별하는 키)
            val friendId = search_id.text.toString()   // 검색할 친구 아이디
            val friendList = arrayListOf<String>()    // 친구 이름 저장할 배열

            // DB에서 아이디 검색해서 친구 이름 가져오는거
            firestore.collection("users")
                .whereEqualTo("id", friendId)  // 아이디 검색을 통해 친구 정보 가져옴
                .get()

                    //성공하면
                .addOnSuccessListener {
                    for(name in it!!.documents) {
                        Log.d("success", name["name"].toString())

                        friendList.add(name["name"].toString())

                        toast(friendList.toString())
                    }
                }

                    //실패하면
                .addOnFailureListener { exception ->
                    Log.d("fail", exception.toString())
                    toast("사용자 정보 없음")
                }

            // 가져온 친구 이름 DB에 저장하기 위해 현재 사용자 아이디 찾기
            // 현재 사용자 아이디를 식별하는 걸로 사용
            // friendList - 현재 사용자 아이디 - 친구 이름(배열)
            firestore.collection("users")
                    .document(currentUser!!)
                    .get()
                    .addOnSuccessListener {
                        val currentUserId = it.get("id").toString() // 현재 사용자 id 가져옴

                        // DB에 저장하기 위한 key(friend_name), value(친구 이름 배열)
                        val friendName = hashMapOf(
                                "friend_name" to friendList
                        )

                        // DB에 저장 friendList - 현재 사용자 아이디 - 친구 이름
                        firestore.collection("friendList").document(currentUserId)
                                .set(friendName)
                                .addOnSuccessListener {
                                    toast("저장 성공")
                                    finish()
                                }
                                .addOnFailureListener {
                                    toast("저장 실패")
                                }
                    }
        }
    }
}