package com.example.pokemonapp.data.local

import androidx.room.*
import com.example.pokemonapp.domain.model.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset ")
    suspend fun getAllPokemons(offset: Int, limit: Int): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE name = :name")
    suspend fun getPokemon(name: String): Pokemon

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Pokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Pokemon)

    @Update(entity = Pokemon::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(character: Pokemon)

}