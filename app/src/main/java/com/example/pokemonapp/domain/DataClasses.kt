package com.example.pokemonapp.domain

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

data class SinglePokemon(
    @field:Json(name = "id") val id : Int?,
    @field:Json(name = "name") val name : String?,
    @field:Json(name = "types") val types : List<PokemonType>?,
    @field:Json(name = "weight") val weight : Int?,
    @field:Json(name = "height") val height : Int?,
    @field:Json(name = "sprites") val sprites : Sprites?,
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