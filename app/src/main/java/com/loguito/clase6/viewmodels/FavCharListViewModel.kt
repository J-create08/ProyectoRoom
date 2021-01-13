package com.loguito.clase6.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.loguito.clase6.db.CharacterDataBase
import com.loguito.clase6.db.FavCharacter
import com.loguito.clase6.repository.CharacterRepository

class FavCharListViewModel(application: Application): AndroidViewModel(application) {
    val repository: CharacterRepository
    val database: CharacterDataBase = CharacterDataBase.getCharacterDataBase(application)

    init {
        repository = CharacterRepository(database.favCharacterDao())
    }

    fun getCharacters(): LiveData<List<FavCharacter>> = repository.allCharacters.asLiveData()
}