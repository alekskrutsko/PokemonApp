package com.example.pokemonapp.domain.useCases

import com.example.pokemonapp.repository.Repository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val repository : Repository) {
    suspend operator fun invoke(offset: Int, limit: Int) = repository.getPokemonList(offset, limit)

}