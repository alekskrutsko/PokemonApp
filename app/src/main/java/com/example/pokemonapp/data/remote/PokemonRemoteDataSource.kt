package com.example.pokemonapp.data.remote

import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(private val pokemonService: PokemonService): BaseDataSource() {

    suspend fun getPokemonList(offset: Int = 0, limit: Int = 50) = getResult { pokemonService.getPokemonList(offset , limit) }
    suspend fun getPokemon(name: String) = getResult { pokemonService.getPokemon(name) }
}