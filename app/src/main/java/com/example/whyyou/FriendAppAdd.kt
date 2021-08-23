package com.example.whyyou

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.friend_app_add.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NAME_SHADOWING")
class FriendAppAdd : AppCompatActivity() {

    private var myCalendar = Calendar.getInstance()


    private var myDatePicker =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_app_add)

        var intent = intent

        friendname.setText(intent.getStringExtra("friend_name"))

        val et_Date = findViewById<View>(R.id.app_datepick) as EditText
        et_Date.setOnClickListener {
            DatePickerDialog(
                    this,
                    myDatePicker,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        val et_time = findViewById<View>(R.id.app_timepick) as EditText
        et_time.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog

            mTimePicker = TimePickerDialog(
                    this,
                    { timePicker, selectedHour, selectedMinute ->
                        var selectedHour = selectedHour
                        var state = "AM"
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12
                            state = "PM"
                        }
                        // EditText에 출력할 형식 지정
                        et_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분")
                    }, hour, minute, false
            ) // true의 경우 24시간 형식의 TimePicker 출현
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        val firestore = Firebase.firestore
        val currentUserEmail = Firebase.auth.currentUser!!.email   // 현재 사용자 email (계정 구별하는 키)

        app_addok.setOnClickListener {

            val appList = hashMapOf(
                    "Title" to app_title.text.toString(),
                    "Friend_name" to friendname.text.toString(),
                    "Date" to et_Date.text.toString(),
                    "Time" to et_time.text.toString(),
                    //장소
            )

            firestore.collection(currentUserEmail!!).document("App List")
                    .collection("App List").add(appList)
                    .addOnSuccessListener {
                        toast("저장 성공")
                    }
                    .addOnFailureListener {
                        toast("저장 실패")
                    }

            //friendname.text.toString()
            //app_title.text.toString()
            //et_Date.text.toString()
            //et_time.text.toString()
            //장소


        }




    }

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd" // 출력형식   2018/11/28
        val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
        val et_date = findViewById<View>(R.id.app_datepick) as EditText
        et_date.setText(sdf.format(myCalendar.time))
    }


}