package com.example.whyyou

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast

@Suppress("NAME_SHADOWING")
class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // 회원가입 버튼 클릭 시
        btnSignUp.setOnClickListener {
            val userEmail = userEmail.text.toString()
            val password = userPassword.text.toString()
            val userId = userId.text.toString()
            val userName = userName.text.toString()

            // 계정 생성
            Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener { task ->
                    // 계정 생성 성공 시 DB에 정보 저장
                    if (task.isSuccessful) {
                        toast("회원가입 성공")

                        val user = hashMapOf(
                            "email" to userEmail,
                            "password" to password,
                            "id" to userId,
                            "name" to userName
                        )

                        val database = FirebaseFirestore.getInstance()
                        val currentUser = Firebase.auth.uid

                        database.collection("users").document(currentUser!!)
                            .set(user)
                            .addOnSuccessListener {
                                toast("저장 성공")
                            }
                            .addOnFailureListener {
                                toast("저장 실패")
                            }

                        finish()

                    } else {
                        toast(task.exception.toString())
                    }
                }
        }
    }
}