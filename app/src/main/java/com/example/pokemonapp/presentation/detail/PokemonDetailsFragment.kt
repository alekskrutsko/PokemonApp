package com.example.pokemonapp.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentDetailBinding
import com.example.pokemonapp.utils.GlideUtils
import com.example.pokemonapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonDetailsFragment: Fragment(R.layout.fragment_detail) {

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) : View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)

        viewModel.getPokemon(navArgs.pokemonName)
        Log.d("pokemonName", navArgs.pokemonName)

        viewModel.pokemon.observe(this.viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    val pokemon = response.data
                    // picture download
                    binding.apply {
                        GlideUtils.loadImage(binding.image, pokemon?.sprites?.frontDefault)
                        name.text = pokemon?.name?.capitalize()
                        height.text = "Height: ${pokemon?.height?.toString()} cm"
                        weight.text = "Weight: ${pokemon?.weight.toString()} kg"
                        types.text = "Type: ${pokemon?.types?.joinToString(
                            separator = ", ",
                            transform = { it.type?.name.toString() }
                        )}"
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Произошла ошибка: ${response.message}", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }

        }

        //inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect { event ->
                when (event) {
                    is PokemonDetailsViewModel.TasksEvent.NavigateToPokeListFragment -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}