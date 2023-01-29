package com.example.pokemonapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.SpritesConverter
import com.example.pokemonapp.domain.model.TypesListConverter

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(TypesListConverter::class, SpritesConverter::class)
abstract class PokemonDB: RoomDatabase()  {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val databaseName = "PokemonDB"
    }
}