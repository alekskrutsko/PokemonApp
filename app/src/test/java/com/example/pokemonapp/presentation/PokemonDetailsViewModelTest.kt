package com.example.pokemonapp.presentation

import com.example.pokemonapp.domain.useCases.GetPokemonUseCase
import com.example.pokemonapp.presentation.detail.PokemonDetailsViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonDetailsViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `should return pokemon detail` (){
        val getPokemonUseCase = mock<GetPokemonUseCase>()
        runBlocking(Dispatchers.Main) { // Will be launched in the mainThreadSurrogate dispatcher
            val viewModel = PokemonDetailsViewModel(getPokemonUseCase)
            viewModel.getPokemon("BULBASAUR")
        }
        runBlocking(Dispatchers.Main) {
            Mockito.verify(getPokemonUseCase, Mockito.times(1)).invoke("BULBASAUR")
        }

    }
}