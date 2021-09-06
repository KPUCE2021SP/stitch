package com.example.tmapapi

class MapPoint(Name: String?, latitude: Double, longitude: Double) {
    var name: String? = null
    var latitude = 0.0
    var longitude = 0.0

    init {
        this.name = Name
        this.latitude = latitude
        this.longitude = longitude
    }
}