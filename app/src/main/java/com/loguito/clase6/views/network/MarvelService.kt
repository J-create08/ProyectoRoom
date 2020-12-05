package com.loguito.clase6.views.network

import com.loguito.clase6.views.network.models.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {
    // TODO 4 Crear interfaz donde se definen los metodos a consumir
    @GET("v1/public/characters")
    fun getCharacterList(@Query("apikey") apiKey: String, @Query("hash") hash: String, @Query("ts") timeStamp: String): Call<CharacterResponse>
}