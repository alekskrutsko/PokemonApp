package com.example.pokemonapp.data.remote

import com.example.pokemonapp.domain.PokemonListResult
import com.example.pokemonapp.domain.SinglePokemon
import com.example.pokemonapp.utils.Resource
import javax.inject.Inject

class PokemonRemoteDataSourceImpl @Inject constructor(private val pokemonService: PokemonService): BaseDataSource(), PokemonRemoteDataSource{

    override suspend fun getPokemonList(offset: Int, limit: Int): Resource<PokemonListResult> = getResult { pokemonService.getPokemonList(offset , limit)}
    override suspend fun getPokemon(name: String): Resource<SinglePokemon> =  getResult { pokemonService.getPokemon(name) }
}