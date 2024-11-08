package com.example.pokemonapp.data.remote

import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.PokemonListResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonListResult>

    @GET("pokemon/{id}/")
    suspend fun getPokemon(@Path("id") name: String): Response<Pokemon>
}