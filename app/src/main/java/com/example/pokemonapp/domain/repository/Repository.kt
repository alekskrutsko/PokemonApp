package com.example.pokemonapp.domain.repository

import com.example.pokemonapp.data.entities.Pokemon
import javax.inject.Singleton

@Singleton
interface Repository {
    suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon>
    suspend fun getPokemon(name: String): Pokemon
}