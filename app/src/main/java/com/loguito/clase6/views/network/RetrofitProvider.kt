package com.loguito.clase6.views.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    // TODO 3 Crear clase que provee el objeto retrofit

    private val baseUrl = "https://gateway.marvel.com:443/"

    val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getMarvelService(): MarvelService = retrofit.create(MarvelService::class.java)
}