package com.xq.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xq.sunnyweather.logic.Repository
import com.xq.sunnyweather.logic.model.Location

//class WeatherViewModel : ViewModel() {
//    private val locationLiveData = MutableLiveData<Location>()
//    var locationLng = ""
//    var locationLat = ""
//    var placeName = ""
//    val weatherLiveData = locationLiveData.switchMap { location ->
//        Repository.refreshWeather(location.lng, location.lat)
//    }
//    fun refreshWeather(lng: String, lat: String) {
//        locationLiveData.value = Location(lng, lat)
//    }
//}

class WeatherViewModel : ViewModel(){
    // 在手机屏幕旋转时保存数据
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

   private var locationLiveData = MutableLiveData<Location>()
    val weatherLiveData = locationLiveData.switchMap {location->
        Repository.refreshWeather(location.lng,location.lat)
    }
    fun refreshWeather(lng:String,lat:String) {
        locationLiveData.value = Location(lng,lat)
    }
}