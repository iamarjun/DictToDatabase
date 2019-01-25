package com.example.dicttodatabase.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface Client {
    @GET
    fun downloadFile(@Url fileUrl: String): Observable<String>
}