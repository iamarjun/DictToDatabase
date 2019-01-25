package com.example.dicttodatabase.network

import com.example.dicttodatabase.utils.CallBack
import okhttp3.ResponseBody
import retrofit2.Response

interface ApiWrapper {
    fun downloadFile(url: String, callBack: CallBack<String>)
}