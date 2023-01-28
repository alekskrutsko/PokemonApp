package com.example.pokemonapp.presentation.list

import com.example.pokemonapp.data.entities.Pokemon

class PokemonClickListener(val clickListener: (pokemon : Pokemon) -> Unit) :
    OnClickListener<Pokemon> {
    override fun onClick(t : Pokemon) = clickListener(t)
}