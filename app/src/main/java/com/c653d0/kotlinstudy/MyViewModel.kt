package com.c653d0.kotlinstudy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val fanJvTimeTable:MutableLiveData<ArrayList<FanJvLinkList>> = MutableLiveData()
    private val getYingHuaData:GetYingHuaData = GetYingHuaData()

    fun getTimeTable(html:String){
        fanJvTimeTable.value =  getYingHuaData.getFanJvTimeTable(html)
    }

    fun getTimeTableLiveData():MutableLiveData<ArrayList<FanJvLinkList>>{
        return fanJvTimeTable
    }
}