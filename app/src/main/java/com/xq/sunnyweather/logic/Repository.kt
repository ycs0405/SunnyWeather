package com.xq.sunnyweather.logic

import androidx.lifecycle.liveData
import com.xq.sunnyweather.logic.model.Place
import com.xq.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

//object Repository {
//    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
//        val result = try {
//            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
//            if (placeResponse.status == "ok") {
//                val places = placeResponse.places
//                Result.success(places)
//            } else {
//                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Place>>(e)
//        }
//        emit(result)
//    }
//}

object Repository{

    //liveData(): 构建LiveData对象并返回，函数里面可以调用挂起函数
    // Dispatchers.IO:指定在非主线程执行
    fun searchPlaces(query:String) = liveData<Result<List<Place>>>(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }

        // emit:通知观察者，数据发生了变化
        emit(result)
    }
}