package com.loguito.clase6.repository

import com.loguito.clase6.db.CharacterDAO
import com.loguito.clase6.db.FavCharacter
import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val characterDAO: CharacterDAO) {
    suspend fun insert(character: FavCharacter) {
        characterDAO.insert(character)
    }

    //TODO: consulta a api
    val allCharacters: Flow<List<FavCharacter>> = characterDAO.getCharacters()

}