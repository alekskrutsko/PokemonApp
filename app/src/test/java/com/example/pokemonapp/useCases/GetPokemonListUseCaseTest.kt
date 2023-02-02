package com.example.pokemonapp.useCases

import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.PokemonType
import com.example.pokemonapp.domain.model.Sprites
import com.example.pokemonapp.domain.model.Type
import com.example.pokemonapp.domain.repository.Repository
import com.example.pokemonapp.domain.useCases.GetPokemonListUseCase
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class GetPokemonListUseCaseTest{

    private val testRepository = mock<Repository>()

    @Test
    fun `should return the pokemonList from the repository`(){
        val testPokemonList = listOf(Pokemon(1, "pikachu", 12, 12, mutableListOf(PokemonType(Type("grass"))), Sprites("")))
        runBlocking{
            Mockito.`when`(testRepository.getPokemonList(0, 30)).thenReturn(testPokemonList)
        }
        val useCase = GetPokemonListUseCase(repository = testRepository)

        val actual = runBlocking{useCase.invoke(0, 30)}
        val expected = testPokemonList

        Assertions.assertEquals(expected, actual)
    }

}