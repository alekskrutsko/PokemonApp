package com.example.pokemonapp.presentation.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.useCases.GetPokemonListUseCase
import com.example.pokemonapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(private val getPokemonListUseCase: GetPokemonListUseCase): ViewModel() {

    var pokemons = MutableLiveData<Resource<List<Pokemon>>>()

    init {
        getPokemonList(0, 30)
    }

    fun getPokemonList(offset:Int, limit:Int) {
        viewModelScope.launch {
            try {
                val pokemonList = getPokemonListUseCase.invoke(offset, limit)
                pokemons.postValue(Resource.success(pokemonList))
            }catch (e: Exception){
                pokemons.postValue(Resource.error(e.message.toString()))
                Log.e("error","getPokemonList error", e)
            }
        }
    }
}
