package com.example.pokemonapp.data.repository

import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.pokemonapp.data.local.PokemonDao
import com.example.pokemonapp.data.remote.PokemonRemoteDataSource
import com.example.pokemonapp.domain.model.Pokemon
import com.example.pokemonapp.domain.repository.Repository
import com.example.pokemonapp.utils.CheckInternetConnection
import com.example.pokemonapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonDao,
    private val context: Context
) : Repository {

    override suspend fun getPokemon(name: String): Pokemon {
        var returnData = Pokemon(null, name, null, null, null, null)
        if (CheckInternetConnection.connectivityStatus(context)) {
            val response = remoteDataSource.getPokemon(name)
            if (response.status == Resource.Status.SUCCESS) {
                if (response.data != null) {
                    try {
                        localDataSource.insert(
                            Pokemon(
                                response.data.id,
                                response.data.name,
                                response.data.weight,
                                response.data.height,
                                response.data.types,
                                response.data.sprites
                            )
                        )
                    } catch (e: SQLiteException) {
                        Log.e("error", "insert error", e)
                    }

                    returnData = Pokemon(
                        response.data.id,
                        response.data.name,
                        response.data.weight,
                        response.data.height,
                        response.data.types,
                        response.data.sprites
                    )

                    Log.i("FromLocal", returnData.toString())
                }
            } else {
                Log.e("remoteDS getPokemon error", response.message.toString())
            }
        } else {
            try {
                returnData = localDataSource.getPokemon(name)
            } catch (e: SQLiteException) {
                Log.e("error", "database reading error", e)
            }
            Log.i("FromLocal", returnData.toString())
        }
        return returnData
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        var returnData = ArrayList<Pokemon>()
        if (CheckInternetConnection.connectivityStatus(context)) {
            val response =
                withContext(Dispatchers.IO) { remoteDataSource.getPokemonList(offset, limit) }

            withContext(Dispatchers.IO) {
                if (response.status == Resource.Status.SUCCESS) {
                    if (response.data != null) {
                        for (elements in response.data.results) {
                            returnData.add(Pokemon(
                                "/[0-9]+/$".toRegex()
                                    .find(elements.url)?.value?.filter { item -> item.isDigit() }
                                    ?.toInt(),
                                elements.name,
                                null, null, null, null
                            ))
                        }

                        try {
                            localDataSource.insertAll(returnData)
                        } catch (e: SQLiteException) {
                            Log.e("error", "insert error", e)
                        }
                    }
                } else {
                    Log.e("remoteDS getPokemonList error", response.message.toString())
                }
                Log.i("FromRemote", returnData.toString())
            }
        } else {
            try {
                returnData = withContext(Dispatchers.IO) {
                    java.util.ArrayList(
                        localDataSource.getAllPokemons(
                            offset,
                            limit
                        )
                    )
                }

            } catch (e: SQLiteException) {
                Log.e("error", "database reading error", e)
            }

            Log.i("FromRemote", returnData.toString())
        }
        return returnData
    }
}