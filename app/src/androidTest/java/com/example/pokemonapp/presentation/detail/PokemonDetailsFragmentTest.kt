package com.example.pokemonapp.presentation.detail

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.filters.MediumTest
import com.example.pokemonapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class PokemonDetailsFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_navigateToPokemonListFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<PokemonDetailsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBackUnconditionally()

        verify(navController).popBackStack()
    }
}