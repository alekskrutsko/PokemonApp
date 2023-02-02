package com.example.pokemonapp.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.pokemonapp.data.local.PokemonDao
import com.example.pokemonapp.data.remote.PokemonRemoteDataSource
import com.example.pokemonapp.domain.model.*
import com.example.pokemonapp.utils.Resource
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class RepositoryImplTest {

    private val remoteDataSource = mock<PokemonRemoteDataSource>()
    private val localDataSource = mock<PokemonDao>()
    private val context = mock<Context>()
    private val connectivityManager = mock<ConnectivityManager>()
    private val capabilities = mock<NetworkCapabilities>()

    @AfterEach
    fun tearDown() {
        Mockito.reset(remoteDataSource)
        Mockito.reset(localDataSource)
        Mockito.reset(context)
    }

    @Test
    fun `should return pokemon info from remote datasource`() {
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork))
            .thenReturn(capabilities)
        Mockito.`when`(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)

        runBlocking {
            Mockito.`when`(remoteDataSource.getPokemon("bulbasaur"))
                .thenReturn(
                    Resource.success(
                        Pokemon(
                            id = 1, name = "bulbasaur", weight = 69, height = 7,
                            types = mutableListOf(
                                PokemonType(type = Type(name = "grass")),
                                PokemonType(type = Type(name = "poison"))
                            ),
                            sprites = Sprites(frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
                        )
                    )
                )
        }

        val repositoryImpl = RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            context = context
        )
        var actual: Pokemon
        runBlocking {
            actual = repositoryImpl.getPokemon("bulbasaur")
        }

        val expected = Pokemon(
            id = 1, name = "bulbasaur", weight = 69, height = 7,
            types = mutableListOf(
                PokemonType(type = Type(name = "grass")),
                PokemonType(type = Type(name = "poison"))
            ),
            sprites = Sprites(frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
        )
        Assertions.assertEquals(expected, actual)
    }


    @Test
    fun `should return pokemon info from local datasource`() {
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork))
            .thenReturn(capabilities)

        runBlocking {
            Mockito.`when`(localDataSource.getPokemon("bulbasaur"))
                .thenReturn(
                    Pokemon(
                        id = 1, name = "bulbasaur", weight = 69, height = 7,
                        types = mutableListOf(
                            PokemonType(type = Type(name = "grass")),
                            PokemonType(type = Type(name = "poison"))
                        ),
                        sprites = Sprites(frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
                    )
                )
        }

        val repositoryImpl = RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            context = context
        )
        var actual: Pokemon
        runBlocking {
            actual = repositoryImpl.getPokemon("bulbasaur")
        }

        val expected = Pokemon(
            id = 1, name = "bulbasaur", weight = 69, height = 7,
            types = mutableListOf(
                PokemonType(type = Type(name = "grass")),
                PokemonType(type = Type(name = "poison"))
            ),
            sprites = Sprites(frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
        )
        Assertions.assertEquals(expected, actual)
    }


    @Test
    fun `should return pokemon list from remote datasource`() {
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork))
            .thenReturn(capabilities)
        Mockito.`when`(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)

        runBlocking {
            Mockito.`when`(remoteDataSource.getPokemonList(0, 30))
                .thenReturn(
                    Resource.success(
                        PokemonListResult(
                            0, "", "",
                            listOf(
                                PokemonListItem(
                                    "bulbasaur",
                                    "https://pokeapi.co/api/v2/pokemon/1/"
                                ),
                                PokemonListItem("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
                            )
                        )

                    )
                )
        }

        val repositoryImpl = RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            context = context
        )
        var actual: List<Pokemon>
        runBlocking {
            actual = repositoryImpl.getPokemonList(0, 30)
        }

        val expected = listOf(
            Pokemon(
                1,
                "bulbasaur",
                null, null, null, null
            ), Pokemon(
                2,
                "ivysaur",
                null, null, null, null
            )
        )

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `should return pokemon list from local datasource`() {
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
        Mockito.`when`(connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork))
            .thenReturn(capabilities)

        runBlocking {
            Mockito.`when`(localDataSource.getAllPokemons(0, 30))
                .thenReturn(
                    listOf(
                        Pokemon(
                            1,
                            "bulbasaur",
                            null, null, null, null
                        ), Pokemon(
                            2,
                            "ivysaur",
                            null, null, null, null
                        )
                    )
                )
        }

        val repositoryImpl = RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            context = context
        )
        var actual: List<Pokemon>
        runBlocking {
            actual = repositoryImpl.getPokemonList(0, 30)
        }

        val expected = listOf(
            Pokemon(
                1,
                "bulbasaur",
                null, null, null, null
            ), Pokemon(
                2,
                "ivysaur",
                null, null, null, null
            )
        )

        Assertions.assertEquals(expected, actual)
    }

}