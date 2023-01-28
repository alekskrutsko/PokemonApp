package com.example.pokemonapp.data.remote

import com.example.pokemonapp.domain.PokemonListResult
import com.example.pokemonapp.domain.SinglePokemon
import com.example.pokemonapp.utils.Resource

interface PokemonRemoteDataSource {
    suspend fun getPokemonList(offset: Int, limit: Int) : Resource<PokemonListResult>
    suspend fun getPokemon(name: String) : Resource<SinglePokemon>
}