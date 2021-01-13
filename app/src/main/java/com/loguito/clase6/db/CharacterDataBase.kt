package com.loguito.clase6.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavCharacter::class), version = 1, exportSchema = false)
public abstract class CharacterDataBase: RoomDatabase() {
    abstract fun favCharacterDao(): CharacterDAO

    companion object {
        @Volatile
        private var INSTANCE: CharacterDataBase? = null

        fun getCharacterDataBase(context: Context): CharacterDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDataBase::class.java,
                    "character_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}