package com.test.tmaptest

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.PointF
import android.content.Context
import android.view.View
import android.widget.LinearLayout


import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.skt.Tmap.*


import kotlin.math.round

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    // 마커에 클릭 이벤트 등록
    // private val eventListener = MarkerEventListener(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt = findViewById(R.id.txt) as TextView // txt를 TextView 변수로 생성

        val linearLayoutTmap = findViewById<View>(R.id.linearLayoutTmap) as LinearLayout
        val tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("l7xx82a7bab2d5db4176a5397e5152c7db58")  // 발급 받은 Api 키 입력

        // tMapView.setPOIItemEventListener(eventListener)  // 지도에 마커 클릭 이벤트 리스너 등록

        linearLayoutTmap.addView(tMapView)

        // 마커 생성
        val markerItem1 = TMapMarkerItem()  // 마커 정보 저장할 함수 선언
        val tMapPoint1 = TMapPoint(37.570841, 126.985302) // SKT타워. (마커의 좌표)
        val bitmap =
            BitmapFactory.decodeResource(this.getResources(), R.drawable.map_pin_red) // 마커 아이콘 설정

        markerItem1.icon = bitmap // 마커 아이콘 지정
        markerItem1.setPosition(0.5f, 1.0f) // 마커의 중심점을 중앙, 하단으로 설정
        markerItem1.tMapPoint = tMapPoint1 // 마커의 좌표 지정
        markerItem1.name = "마커" // 마커의 타이틀 지정

        tMapView.addMarkerItem("markerItem1", markerItem1) // 지도에 마커 추가
        tMapView.setCenterPoint(126.985302, 37.570841)

        // 직선 경로 그리기
        val alTMapPoint = ArrayList<TMapPoint>()

        alTMapPoint.add(TMapPoint(37.570841, 126.985302)) // SKT타워
        alTMapPoint.add(TMapPoint(37.551135, 126.988205)) // N서울타워

        val tMapPolyLine = TMapPolyLine()
        tMapPolyLine.lineColor = Color.BLUE
        tMapPolyLine.lineWidth = 2f
        for (i in 0 until alTMapPoint.size) {
            tMapPolyLine.addLinePoint(alTMapPoint[i])
        }
        tMapView.addTMapPolyLine("Line1", tMapPolyLine)

        val distance : Double=round( DistanceByDegree(37.570841, 126.985302, 37.551135, 126.988205)*100)/100


        // 도보 시속 4km 기준
        val Walk_time : Double = distance/4.0   // 거리/속력 = 시간
        // 정수는 x시간 처리, 소수만 꺼낸 정수 y는 공식 (y x 60)/100 해서 나온 값을 toInt. (최종 값 소수점 버림)

        val time : Int = Walk_time.toInt()   // 소수점 버림한 정수부만 추출

        val min : Double = Walk_time - time  // 전체 부분에서 정수부 빼기
        //val min2 : Int = ((min*60)/100).toInt()
        val min2 : Int = (60*min).toInt()

        // 거리 텍스트로 출력
        if(time != 0)
        txt.text = ("거리 : " + distance.toString() + " km\n"+"도보 : "
                + time.toString()  + "시간 :"+ min2.toString() + " 분")
        else
        txt.text = ("거리 : " + distance.toString() + "km\n"+"도보 : "+ min2.toString() + " 분")
    }

    // 두 지점 위도, 경도 받아서 거리 계산
    private fun DistanceByDegree(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        dist = dist * 1.609344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    /*
    // 마커 관련
    class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.marker, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName   // 해당 마커의 정보 이용 가능
            address.text = "getCalloutBalloon"  // 텍스트에 적을 내용
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            address.text = "getPressedCalloutBalloon" // 텍스트에 적을 내용
            return mCalloutBalloon
        }
    }

    // 마커 클릭 이벤트 리스너
    class MarkerEventListener(val context: Context): TMapView.POIItemEventListener {
        override fun onPOIItemSelected(tMapView: TMapView?, poiItem: tMapPOIItem?) {
            // 마커 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(tMapView: TMapView?, poiItem: MapPOIItem?) {
            // 말풍선 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(tMapView: TMapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
            // 말풍선 클릭 시 삭제 or 실행 취소
            val builder = AlertDialog.Builder(context)
            val itemList = arrayOf("마커 삭제", "취소")
            builder.setTitle("${poiItem?.itemName}")
            builder.setItems(itemList) { dialog, which ->
                when(which) {
                    0 -> tMapView?.removePOIItem(poiItem)    // 마커 삭제
                    1 -> dialog.dismiss()   // 대화상자 닫기
                    else -> Toast.makeText(context, "토스트", Toast.LENGTH_SHORT).show()
                    // else 달지 않으면 오류 생기므로, 임의 삽입.
                }
            }
            builder.show()
        }
    }

     */
}