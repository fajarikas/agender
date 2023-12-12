package com.fajarikasaputra.agender.api


import com.fajarikasaputra.agender.ResponseMorty
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("character")

    fun getPhotos(): Call<ResponseMorty>
}