package com.example.pokemonapp.data.repository

import android.content.Context
import android.util.Log
import com.example.pokemonapp.data.entities.Pokemon
import com.example.pokemonapp.data.local.PokemonDao
import com.example.pokemonapp.data.remote.PokemonRemoteDataSource
import com.example.pokemonapp.domain.repository.Repository
import com.example.pokemonapp.utils.CheckInternetConnection
import com.example.pokemonapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val remoteDataSource: PokemonRemoteDataSource,
                                         private val localDataSource: PokemonDao,
                                         private val context: Context
): Repository {

    override suspend fun getPokemon(name: String): Pokemon {
        var returnData = Pokemon(null, name, null, null, null, null)
        if (CheckInternetConnection.connectivityStatus(context)) {
            val character = remoteDataSource.getPokemon(name)
            if(character.status == Resource.Status.SUCCESS){
                if (character.data != null){
                    Log.i("Character id", character.data.id.toString())
                    localDataSource.insert(
                        Pokemon(
                            character.data.id,
                            character.data.name,
                            character.data.weight,
                            character.data.height,
                            character.data.types?.joinToString(
                                separator = ", ",
                                transform = { it.type?.name.toString() }
                            ),
                            character.data.sprites?.frontDefault
                        )
                    )
                    returnData = Pokemon(
                        character.data.id,
                        character.data.name,
                        character.data.weight,
                        character.data.height,
                        character.data.types?.joinToString(
                            separator = ", ",
                            transform = { it.type?.name.toString() }
                        ),
                        character.data.sprites?.frontDefault
                    )
                    Log.i("FromLocal", returnData.toString())
                }
            }
        } else {
            returnData = localDataSource.getPokemon(name)
            Log.i("FromLocal", returnData.toString())
        }
        return returnData
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        var returnData = ArrayList<Pokemon>()
        if (CheckInternetConnection.connectivityStatus(context)) {
            val response = withContext(Dispatchers.IO) { remoteDataSource.getPokemonList(offset, limit) }
            withContext(Dispatchers.IO) {
                if(response.status == Resource.Status.SUCCESS){
                    if (response.data != null){
                        for (elements in response.data.results){
                            returnData.add(Pokemon(
                                "/[0-9]+/$".toRegex()
                                    .find(elements.url)?.value?.filter { item -> item.isDigit()}
                                    ?.toInt(),
                                elements.name,
                                null, null, null, null
                            ))
                        }
                        localDataSource.insertAll(returnData)
                    }
                }
                Log.i("FromRemote", returnData.toString())
            }
        } else {
            returnData = withContext(Dispatchers.IO) { localDataSource.getAllPokemons(offset, limit) } as ArrayList<Pokemon>
            Log.i("FromRemote", returnData.toString())
        }
        return returnData
    }
}