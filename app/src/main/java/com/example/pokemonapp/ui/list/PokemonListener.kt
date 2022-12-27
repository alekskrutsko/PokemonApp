package com.example.pokemonapp.ui.list

import com.example.pokemonapp.data.entities.Pokemon

class PokemonListener(val clickListener: (pokemon : Pokemon) -> Unit) {
    fun onClick(pokemon : Pokemon) = clickListener(pokemon)
}