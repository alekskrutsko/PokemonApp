package com.example.pokemonapp.domain.model

import com.squareup.moshi.Json

data class PokemonListResult(
    @field:Json(name = "count") val count : Int,
    @field:Json(name = "next") val next : String?,
    @field:Json(name = "previous") val previous : String?,
    @field:Json(name = "result") val results : List<PokemonListItem>
)

data class PokemonListItem(
    @field:Json(name = "name") val name : String,
    @field:Json(name = "url") val url : String
)

data class PokemonType(
    @field:Json(name = "type") val type : Type?
)

data class Type(
    @field:Json(name = "name") val name : String?
)

data class Sprites(
    @Json(name = "front_default") val frontDefault : String?
)