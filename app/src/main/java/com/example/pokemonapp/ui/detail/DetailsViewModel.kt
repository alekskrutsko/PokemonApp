package com.example.pokemonapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.entities.Pokemon
import com.example.pokemonapp.domain.useCases.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase): ViewModel() {

    val pokemon = MutableLiveData<Pokemon>()

    fun getPokemon(name: String){
        viewModelScope.launch {
            pokemon.postValue(getPokemonUseCase.invoke(name))
        }
    }
}