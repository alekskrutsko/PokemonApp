package com.example.pokemonapp.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.entities.Pokemon
import com.example.pokemonapp.domain.useCases.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(private val getPokemonListUseCase: GetPokemonListUseCase): ViewModel() {

    var pokemons = MutableLiveData<List<Pokemon>>()

    init {
        getPokemonList(0, 30)
    }

    fun getPokemonList(offset:Int, limit:Int) {
        viewModelScope.launch {
            pokemons.postValue(getPokemonListUseCase.invoke(offset, limit))
        }
    }
}
