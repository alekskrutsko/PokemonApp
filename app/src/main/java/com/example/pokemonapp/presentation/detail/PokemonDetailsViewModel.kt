package com.example.pokemonapp.presentation.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.useCases.GetPokemonUseCase
import com.example.pokemonapp.presentation.list.PokemonListViewModel
import com.example.pokemonapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase): ViewModel() {

    val pokemon = MutableLiveData<Resource<Pokemon>>()
    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    fun getPokemon(name: String){
        viewModelScope.launch {
            try {
                val pokemonDetail = getPokemonUseCase.invoke(name)
                pokemon.postValue(Resource.success(pokemonDetail))
            }catch (e: Exception){
                pokemon.postValue(Resource.error(e.message.toString()))
                Log.e("error","getPokemon error", e)
            }

        }
    }

    fun onBackPressed() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateToPokeListFragment)
    }

    sealed class TasksEvent {
        object NavigateToPokeListFragment: TasksEvent()
    }
}