package com.example.pokemonapp.data.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

object ImageBitmapStringConverter {
    @TypeConverter
    fun BitMapToString(bitmap : Bitmap) : String {
        val baos = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        val b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT) ?: ""
    }

    @TypeConverter
    fun StringToBitMap(encodedString : String) : Bitmap {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        return (BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size) ?: null) as Bitmap
    }
}