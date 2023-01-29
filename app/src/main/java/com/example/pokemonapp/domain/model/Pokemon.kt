package com.example.pokemonapp.domain.model

import androidx.room.*
import com.squareup.moshi.Json

@Entity(tableName = "pokemon")
data class Pokemon(
    @field:Json(name = "id") @PrimaryKey @ColumnInfo(name = "id") val id: Int?,
    @field:Json(name = "name") @ColumnInfo(name = "name") val name: String?,
    @field:Json(name = "weight") @ColumnInfo(name = "weight") val weight: Int?,
    @field:Json(name = "height") @ColumnInfo(name = "height") val height: Int?,
    @TypeConverters(TypesListConverter::class)
    @field:Json(name = "types") @ColumnInfo(name = "types") val types: MutableList<PokemonType>?,
    @TypeConverters(SpritesConverter::class)
    @field:Json(name = "sprites") @ColumnInfo(name = "image") val sprites: Sprites?,
)

class TypesListConverter {
    @TypeConverter
    fun fromTypes(list: MutableList<PokemonType>?): String? {
        return list?.map{it.type?.name }?.joinToString(", ")
    }

    @TypeConverter
    fun toTypes(data: String?): MutableList<PokemonType>? {
        return data?.split(", ")?.map {PokemonType(Type(it)) }?.toMutableList()
    }
}

class SpritesConverter {
    @TypeConverter
    fun fromSprites(sprite: Sprites?): String? {
        return sprite?.frontDefault
    }

    @TypeConverter
    fun toSprites(data: String?): Sprites {
        return Sprites(data)
    }
}
