package com.loguito.clase6.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.loguito.clase6.db.CharacterDataBase
import com.loguito.clase6.db.FavCharacter
import com.loguito.clase6.repository.CharacterRepository
import kotlinx.coroutines.launch

class SaveCharViewModel(application: Application): AndroidViewModel(application){
    val repository: CharacterRepository
    val database: CharacterDataBase

    init {
        database = CharacterDataBase.getCharacterDataBase(application)
        repository = CharacterRepository(database.favCharacterDao())
    }

    fun insertCharacter(character:FavCharacter){
        viewModelScope.launch {
            repository.insert(character)
        }
    }
}