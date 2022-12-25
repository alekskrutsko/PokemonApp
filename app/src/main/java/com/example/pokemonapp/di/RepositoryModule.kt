package com.example.pokemonapp.di

import android.content.Context
import com.example.pokemonapp.data.local.PokemonDao
import com.example.pokemonapp.data.remote.PokemonRemoteDataSource
import com.example.pokemonapp.repository.Repository
import com.example.pokemonapp.repository.RepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: PokemonRemoteDataSource, localDataSource: PokemonDao, context: Context): Repository {
        return RepositoryImpl(remoteDataSource, localDataSource, context)
    }
}