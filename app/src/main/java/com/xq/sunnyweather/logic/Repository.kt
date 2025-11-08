package com.xq.sunnyweather.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.xq.sunnyweather.logic.dao.PlaceDao
import com.xq.sunnyweather.logic.model.Place
import com.xq.sunnyweather.logic.model.Weather
import com.xq.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.math.ln

//object Repository{
//
//    //liveData(): 构建LiveData对象并返回，函数里面可以调用挂起函数
//    // Dispatchers.IO:指定在非主线程执行
//    fun searchPlaces(query:String) = liveData<Result<List<Place>>>(Dispatchers.IO) {
//        val result = try {
//            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
//            if (placeResponse.status == "ok"){
//                val places = placeResponse.places
//                Result.success(places)
//            }else{
//                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
//            }
//        }catch (e:Exception){
//            Result.failure<List<Place>>(e)
//        }
//
//        // emit:通知观察者，数据发生了变化
//        emit(result)
//    }
//
//    //liveData(): 构建LiveData对象并返回，函数里面可以调用挂起函数
//    // Dispatchers.IO:指定在非主线程执行
//    fun refreshWeather(lng:String,lat:String) = liveData(Dispatchers.IO) {
//        val result = try{
//            // 通过async调用异步函数，必须加协程作用域coroutineScope
//            coroutineScope {
//                // async:调用挂起函数，不用等结果
//                val deferredRealtime = async {
//                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
//                }
//                val deferredDaily = async {
//                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
//                }
//
//                // 获取挂起函数的执行结果
//                val realtimeResponse = deferredRealtime.await()
//                val dailyResponse = deferredDaily.await()
//
//                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
//                    // 将有用的数据进行打包，并返回给观察者
//                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
//                    Result.success(weather)
//                }else{
//                    Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status},daily response status is ${dailyResponse.status}"))
//                }
//            }
//        }catch (e:Exception){
//            Result.failure<Weather>(e)
//        }
//        // emit():将结果发射出去，告诉观察者，数据发生了变化
//        emit(result)
//    }
//
//}

object Repository {
    fun savePlace(place:Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() : Place = PlaceDao.getSavedPlace()
    fun isPlaceSaved() : Boolean = PlaceDao.isPlaceSaved()

    fun searchPlaces(query:String) = fire(Dispatchers.IO){
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO){
        coroutineScope {
            val deferredRealtime = async {
                Log.d("YCS", "getRealtimeWeather lng:${lng},lat:${lat}")
                SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
            }
//            val deferredDaily = async {
//                Log.d("YCS", "getDailyWeather lng:${lng},lat:${lat}")
//                SunnyWeatherNetwork.getDailyWeather(lng,lat)
//            }
            val realtimeResponse = deferredRealtime.await()
            if(realtimeResponse.status == "ok"){
                Log.d("YCS", "bbb realtimeResponse.result.realtime: ${realtimeResponse.result.realtime.skycon},$${realtimeResponse.result.realtime.temperature}")
            }

//            val dailyResponse = deferredDaily.await()
//            if(dailyResponse.status == "ok"){
//                Log.d("YCS", "ccc dailyResponse.result.daily: ${dailyResponse.result.daily.toString()}")
//            }

            if (realtimeResponse.status == "ok" ){
//                val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                val weather = Weather(realtimeResponse.result.realtime,null)
                Result.success(weather)
            }else{
                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}"))
//                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status},daily response status is ${dailyResponse.status}"))
            }
        }
    }

    private fun <T> fire(context:CoroutineContext,block:suspend ()->Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            }catch (e:Exception){
                Result.failure<T>(e)
            }
            emit(result)
        }
}