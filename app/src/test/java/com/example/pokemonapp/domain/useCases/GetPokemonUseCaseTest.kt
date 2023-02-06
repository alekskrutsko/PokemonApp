package com.example.pokemonapp.domain.useCases

import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.model.PokemonType
import com.example.pokemonapp.domain.model.Sprites
import com.example.pokemonapp.domain.model.Type
import com.example.pokemonapp.domain.repository.Repository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class GetPokemonUseCaseTest{

    val testRepository = mock<Repository>()

    @Test
    fun `should return the pokemon from the repository`(){
        val testPokemon = Pokemon(1, "pikachu", 12, 12, mutableListOf(PokemonType(Type("grass"))), Sprites(""))
        runBlocking{
            Mockito.`when`(testRepository.getPokemon("pikachu")).thenReturn(testPokemon)
        }
        val useCase = GetPokemonUseCase(repository = testRepository)

        val actual = runBlocking{useCase.invoke("pikachu")}
        val expected = testPokemon

        Assertions.assertEquals(expected, actual)
    }

}