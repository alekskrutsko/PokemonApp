package com.example.pokemonapp.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.useCases.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase): ViewModel() {

    val pokemon = MutableLiveData<Pokemon>()

    fun getPokemon(name: String){
        viewModelScope.launch {
            pokemon.postValue(getPokemonUseCase.invoke(name))
        }
    }
}