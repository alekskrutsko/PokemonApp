package com.example.pokemonapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pokemonapp.data.local.PokemonDB
import com.example.pokemonapp.data.local.PokemonDao
import com.example.pokemonapp.data.remote.PokemonRemoteDataSource
import com.example.pokemonapp.data.remote.PokemonService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokemonDB {
        return Room.databaseBuilder(
            context,
            PokemonDB::class.java,
            PokemonDB.databaseName,
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideDao(pokemonDatabase: PokemonDB): PokemonDao {
        return pokemonDatabase.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(jsonAdapter: Converter.Factory) : Retrofit {
        val okHttpClient by lazy {
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
        }
        return Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(jsonAdapter)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun jsonAdapter() : Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): PokemonService = retrofit.create(PokemonService::class.java)

    @Provides
    @Singleton
    fun providePokemonRemoteDataSource(pokemonService: PokemonService) = PokemonRemoteDataSource(pokemonService)

}