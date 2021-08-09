package com.test.kakaomaps

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.app.AlertDialog
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {

    // 마커 클릭 이벤트 리스너
    private val eventListener = MarkerEventListener(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 각종 요소 (지도) 등을 받아옴.
        val mapView = MapView(this)
        val mapViewContainer = findViewById<View>(R.id.map_view) as ViewGroup

        mapViewContainer.addView(mapView)  // 지도 화면에 출력

        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))  // 커스텀 말풍선 등록
        mapView.setPOIItemEventListener(eventListener)  // 마커 클릭 이벤트 리스너 등록

        val marker = MapPOIItem() // 마커 추가
        marker.apply {
            itemName = "마커"  // 클릭시 마커에 나타나는 메시지 (마커 이름)
            mapPoint = MapPoint.mapPointWithGeoCoord(37.5666805.toDouble(), 126.9784147.toDouble())
            markerType = MapPOIItem.MarkerType.BluePin  // 미클릭시 마커 모양
            selectedMarkerType = MapPOIItem.MarkerType.RedPin  // 클릭시 마커 모양
        }
        mapView.addPOIItem(marker)
        
        marker.isDraggable = true // 드래그 가능하게 하는 명령
    }


    class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.marker, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_address)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName   // 해당 마커의 정보 이용 가능
            address.text = "getCalloutBalloon"
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            address.text = "getPressedCalloutBalloon"
            return mCalloutBalloon
        }
    }

    // 마커 클릭 이벤트 리스너
    class MarkerEventListener(val context: Context): MapView.POIItemEventListener {
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // 마커 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
            // 말풍선 클릭 시
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
            // 말풍선 클릭 시 삭제 or 실행 취소
            val builder = AlertDialog.Builder(context)
            val itemList = arrayOf("마커 삭제", "취소")
            builder.setTitle("${poiItem?.itemName}")
            builder.setItems(itemList) { dialog, which ->
                when(which) {
                    0 -> mapView?.removePOIItem(poiItem)    // 마커 삭제
                    1 -> dialog.dismiss()   // 대화상자 닫기
                    else -> Toast.makeText(context, "토스트", Toast.LENGTH_SHORT).show()
                    // else 달지 않으면 오류 생기므로, 임의 삽입.
                }
            }
            builder.show()
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
            // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
        }
    }
}