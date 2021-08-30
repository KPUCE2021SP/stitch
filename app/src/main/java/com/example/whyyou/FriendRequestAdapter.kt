package com.example.whyyou

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friend_request_listview.view.*

class FriendRequestAdapter(private val context: Context) : RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>(){

    private var datas = mutableListOf<FriendRequestData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.friend_request_listview, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val friendName = itemView.request_friend_name


        fun bind(item: FriendRequestData) {
            friendName.text = item.name

            itemView.request_ok.setOnClickListener {
                Toast.makeText(context, "수락", Toast.LENGTH_SHORT).show()
                myFriendList(item.name)
            }

            itemView.setOnClickListener {
                val intent = Intent(context,FriendAppAdd::class.java)
                intent.putExtra("friend_name", item.name)
                context.startActivity(intent)
            }

        }

    }

    fun replaceList(newList: MutableList<FriendRequestData>) {
        datas = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun myFriendList(name: String) {
        val firestore = Firebase.firestore
        val currentUserEmail = Firebase.auth.currentUser!!.email

        firestore.collection(currentUserEmail!!).document("Friend List")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                        firestore.collection(currentUserEmail).document("Friend List")
                            .update("friend_name", FieldValue.arrayUnion(name))
                            .addOnSuccessListener {
                                Toast.makeText(context, "저장 성공", Toast.LENGTH_SHORT).show()
                            }
                    }
                    else {
                        val friendList = arrayListOf<String>()    // 친구 이름 저장할 배열

                        friendList.add(name)
                        val friendName = hashMapOf("friend_name" to friendList)

                        firestore.collection(currentUserEmail).document("Friend List")
                            .set(friendName)
                            .addOnSuccessListener {
                                Toast.makeText(context, "저장 성공", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "저장 실패", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "저장 실패", Toast.LENGTH_SHORT).show()
            }
    }
}