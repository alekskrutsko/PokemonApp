package com.example.pokemonapp.presentation

import com.example.pokemonapp.domain.useCases.GetPokemonListUseCase
import com.example.pokemonapp.presentation.list.PokemonListViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.*
import org.mockito.Mockito

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonListViewModelTest {

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
    fun `should return pokemon list` (){
        val getPokemonListUseCase = mock<GetPokemonListUseCase>()
        runBlocking(Dispatchers.Main){ // Will be launched in the mainThreadSurrogate dispatcher
                PokemonListViewModel(getPokemonListUseCase)
        }
        runBlocking(Dispatchers.Main) {
            Mockito.verify(getPokemonListUseCase, Mockito.times(1)).invoke(0, 30)
        }
    }
}