package com.example.friend_app_add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.friend_app_add.*
import java.text.SimpleDateFormat
import java.util.*


class FriendAppAdd : AppCompatActivity() {

    private var myCalendar = Calendar.getInstance()

    private var myDatePicker =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_app_add)

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






    }

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd" // 출력형식   2018/11/28
        val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
        val et_date = findViewById<View>(R.id.app_datepick) as EditText
        et_date.setText(sdf.format(myCalendar.time))
    }


}
