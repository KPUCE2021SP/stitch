package com.example.tmapapi

import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapGpsManager.onLocationChangedCallback
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView


class MainActivity : AppCompatActivity(), onLocationChangedCallback {
    private val TrackingMode = true
    private var tMapView: TMapView? = null
    private var tmapgps: TMapGpsManager? = null
    private val TMapAPIKey = "l7xxa9aa43c6f00e4c02b8812a7e4a5df6c9"

    var mapPoint = arrayListOf<MapPoint>(
        MapPoint("경복궁",126.976998, 37.579600),
        MapPoint("SKT 타워", 126.985302, 37.570841),
        MapPoint("N서울 타워", 126.988205, 37.551135)
    )

    override fun onLocationChange(location: Location) {
        if (TrackingMode) {
            tMapView!!.setLocationPoint(location.longitude, location.latitude)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val linearLayoutTmap = findViewById<View>(R.id.linearLayoutTmap) as LinearLayout


        tMapView = TMapView(this)
        tMapView!!.setHttpsMode(true)
        tMapView!!.setSKTMapApiKey(TMapAPIKey)
        linearLayoutTmap.addView(tMapView)
        setUpMap()
        //tMapView!!.setCompassMode(true)
        tMapView!!.setIconVisibility(true)
        tMapView!!.zoomLevel = 8
        tMapView!!.mapType = TMapView.MAPTYPE_STANDARD
        tMapView!!.setLanguage(TMapView.LANGUAGE_KOREAN)
        tmapgps = TMapGpsManager(this@MainActivity)
        tmapgps!!.minTime = 1000
        tmapgps!!.minDistance = 5f
        tmapgps!!.provider = TMapGpsManager.NETWORK_PROVIDER
        tmapgps!!.OpenGps()
        tMapView!!.setTrackingMode(true)
        tMapView!!.setSightVisible(true)
    }

    private fun setUpMap() {
        /*
        for (i in mapPoint.indices) {
            for (entity in mapPoint) {
                val point = TMapPoint(mapPoint[i].latitude, mapPoint[i].longitude)
                val markerItem1 = TMapMarkerItem()
                markerItem1.setPosition(0.5f, 1.0f)
                markerItem1.tMapPoint = point
                markerItem1.name = entity.name
                tMapView!!.setCenterPoint(mapPoint[i].longitude, mapPoint[i].latitude)
                tMapView!!.addMarkerItem("markerItem1$i", markerItem1)
            }
        }*/
        val bitmap = BitmapFactory.decodeResource(
            this.resources, R.drawable.ic_launcher_background
        )
        for (i in mapPoint.indices){
            Log.d("관광지","${mapPoint[i].name}")
            val point = TMapPoint(mapPoint[i].latitude, mapPoint[i].longitude)
            val markerItem1 = TMapMarkerItem()
            markerItem1.icon = bitmap
            markerItem1.setPosition(0.5f, 1.0f)
            markerItem1.tMapPoint = point
            markerItem1.name = mapPoint[i].name
            tMapView!! .addMarkerItem("markerItem$i",markerItem1)
            tMapView!!.setCenterPoint(mapPoint[i].longitude, mapPoint[i].latitude)
        }
    }
}