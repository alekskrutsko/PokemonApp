package com.example.pokemonapp.presentation.list

import com.example.pokemonapp.domain.model.Pokemon

interface OnClickListener{
    fun onClick(pokemon : Pokemon)
}