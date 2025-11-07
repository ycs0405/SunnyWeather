package com.xq.sunnyweather.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.xq.sunnyweather.logic.Repository
import com.xq.sunnyweather.logic.model.Place

//class PlaceViewModel : ViewModel() {
//    private val searchLiveData = MutableLiveData<String>()
//    val placeList = ArrayList<Place>()
//    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
//        Repository.searchPlaces(query)
//    }
//    fun searchPlaces(query: String) {
//        searchLiveData.value = query
//    }
//}

class PlaceViewModel : ViewModel(){
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()

    val placeLiveData = searchLiveData.switchMap { query->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query:String){
        searchLiveData.value = query
    }
}