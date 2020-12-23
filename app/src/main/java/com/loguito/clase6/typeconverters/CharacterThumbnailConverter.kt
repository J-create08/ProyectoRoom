package com.loguito.clase6.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.loguito.clase6.network.models.CharacterThumbnail

class CharacterThumbnailConverter {
    @TypeConverter
    fun fromJson(value: String?): CharacterThumbnail? {
        val gson = Gson()
        return gson.fromJson(value, CharacterThumbnail::class.java)
    }

    @TypeConverter
    fun characterThumbnailToJson(characterThumbnail: CharacterThumbnail?): String? {
        val gson = Gson()
        return gson.toJson(characterThumbnail)
    }
}