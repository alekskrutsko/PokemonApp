package com.example.pokemonapp.domain.useCases

import com.example.pokemonapp.domain.repository.Repository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(name: String) = repository.getPokemon(name)
}