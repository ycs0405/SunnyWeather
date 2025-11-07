package com.xq.sunnyweather.logic.network

import android.util.Log
import retrofit2.Call
import com.xq.sunnyweather.logic.model.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//object SunnyWeatherNetwork {
//    private val placeService = ServiceCreator.create<PlaceService>()
//    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
//    private suspend fun <T> Call<T>.await(): T {
//        return suspendCoroutine { continuation ->
//            enqueue(object : Callback<T> {
//                override fun onResponse(call: Call<T>, response: Response<T>) {
//                    val body = response.body()
//                    if (body != null) continuation.resume(body)
//                    else continuation.resumeWithException(
//                        RuntimeException("response body is null"))
//                }
//                override fun onFailure(call: Call<T>, t: Throwable) {
//                    continuation.resumeWithException(t)
//                }
//            })
//        }
//    }
//}

object SunnyWeatherNetwork{
    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query:String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await() : T {
        return suspendCoroutine { continuation ->
           // enqueue(): 发起异步请求，挂起协程
           enqueue(object : Callback<T>{
               override fun onResponse(call: Call<T>, response: Response<T>) {
                   val body = response.body()
                   if(body != null){
                       Log.d("YCS", "enqueue onResponse body: ${body}")
                       // 请求完成，恢复协程
                       continuation.resume(body)
                   }else{
                       continuation.resumeWithException(RuntimeException("response body is null"))
                   }
               }

               override fun onFailure(call: Call<T>, t: Throwable) {
                   // 请求失败，抛出异常
                   continuation.resumeWithException(t)
               }

           })
        }
    }
}