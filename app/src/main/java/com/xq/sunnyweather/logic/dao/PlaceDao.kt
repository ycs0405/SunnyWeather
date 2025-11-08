package com.xq.sunnyweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.xq.sunnyweather.SunnyWeatherApplication
import com.xq.sunnyweather.logic.model.Place
import com.xq.sunnyweather.logic.network.PlaceService
import com.xq.sunnyweather.logic.network.SunnyWeatherNetwork

//object PlaceDao {
//    fun savePlace(place: Place) {
//        sharedPreferences().edit {
//            putString("place", Gson().toJson(place))
//        }
//    }
//    fun getSavedPlace(): Place {
//        val placeJson = sharedPreferences().getString("place", "")
//        return Gson().fromJson(placeJson, Place::class.java)
//    }
//    fun isPlaceSaved() = sharedPreferences().contains("place")
//    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
//}

object PlaceDao {
    fun savePlace(place:Place){
        val sharePref = sharePreferences()
        sharePref.edit {
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace() : Place{
        val sharePref = sharePreferences()
        val place = Gson().fromJson<Place>(sharePref.getString("place",""),Place::class.java)
        return place
    }

    fun isPlaceSaved() : Boolean {
        val sharePref = sharePreferences()
        return sharePref.contains("place")
    }

    private fun sharePreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)
}