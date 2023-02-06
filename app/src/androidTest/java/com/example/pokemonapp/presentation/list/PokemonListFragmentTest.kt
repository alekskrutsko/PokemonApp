package com.example.pokemonapp.presentation.list

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.pokemonapp.MyViewAction
import com.example.pokemonapp.R
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
class PokemonListFragmentTest{


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun pokemonSelected_navigateToPokemonDetailFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<PokemonListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Thread.sleep(2000)
        onView(withId(R.id.pokemonRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, MyViewAction.clickChildViewWithId(R.id.card)))
        verify(navController).navigate(
            PokemonListFragmentDirections.actionPokeListFragmentToPokeDetailsFragment("bulbasaur")
        )
    }


}