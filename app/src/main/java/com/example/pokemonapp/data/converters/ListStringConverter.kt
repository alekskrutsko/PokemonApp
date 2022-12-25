package com.example.pokemonapp.data.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.stream.Collectors


object ListStringConverter {
    @TypeConverter
    fun ListToString(list : List<String?>) : String {
        return list.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun StringToList(data : String) : List<String> {
        return listOf(*data.split(",").toTypedArray())
    }
}