package com.loguito.clase6.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitProvider {
    // TODO 3 Crear clase que provee el objeto retrofit

    private val baseUrl = "https://gateway.marvel.com:443/"

    val retrofit: Retrofit

    init {
        // TODO: Agregar logging a los llamados al servidor
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    fun getMarvelService(): MarvelService = retrofit.create(MarvelService::class.java)
}