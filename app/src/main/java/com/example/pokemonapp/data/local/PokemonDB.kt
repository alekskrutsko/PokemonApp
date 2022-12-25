package com.example.pokemonapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemonapp.data.converters.ImageBitmapStringConverter
import com.example.pokemonapp.data.entities.Pokemon

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(ImageBitmapStringConverter::class)
abstract class PokemonDB: RoomDatabase()  {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val databaseName = "PokemonDB"
    }
}