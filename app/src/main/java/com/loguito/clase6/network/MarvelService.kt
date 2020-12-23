package com.loguito.clase6.network

import com.loguito.clase6.network.models.CharacterResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {
    // TODO 4 Crear interfaz donde se definen los metodos a consumir
    @GET("v1/public/characters")
    fun getCharacterList(
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") timeStamp: String
    ): Observable<CharacterResponse>

    @GET("v1/public/characters/{characterId}")
    fun getCharacterDetail(
        @Path("characterId") charId: Int,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") timeStamp: String
    ): Observable<CharacterResponse>
}