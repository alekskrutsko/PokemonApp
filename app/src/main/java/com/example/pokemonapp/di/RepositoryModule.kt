package com.example.pokemonapp.di

import android.content.Context
import com.example.pokemonapp.data.local.PokemonDao
import com.example.pokemonapp.data.remote.PokemonRemoteDataSourceImpl
import com.example.pokemonapp.data.repository.RepositoryImpl
import com.example.pokemonapp.domain.repository.Repository
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
    fun provideRepository(remoteDataSource: PokemonRemoteDataSourceImpl, localDataSource: PokemonDao, context: Context): Repository {
        return RepositoryImpl(remoteDataSource, localDataSource, context)
    }
}