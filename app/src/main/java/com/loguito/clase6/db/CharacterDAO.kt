package com.loguito.clase6.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {
    @Insert
    suspend fun insert(character: FavCharacter)

    @Query("SELECT * FROM FavCharacter")
    fun getCharacters(): Flow<List<FavCharacter>>
}