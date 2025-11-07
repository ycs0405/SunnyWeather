package com.xq.sunnyweather.logic.network

import com.xq.sunnyweather.SunnyWeatherApplication
import com.xq.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceService{

//    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN&query=:query")
//    fun searchPlaces(@Path("query") query:String) :Call<PlaceResponse>

//    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
//    fun searchPlaces(@Query("query") query:String) : Call<PlaceResponse>

    //@Query:动态传参，参数放在方法签名里面
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String) : Call<PlaceResponse>

}

















