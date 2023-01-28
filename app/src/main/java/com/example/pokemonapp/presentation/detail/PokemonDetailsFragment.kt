package com.example.pokemonapp.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonDetailsFragment: Fragment(R.layout.fragment_detail) {

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) : View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)

        toolbar.setNavigationOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        viewModel.getPokemon(navArgs.pokemonName)
        Log.d("pokemonName", navArgs.pokemonName)

        viewModel.pokemon.observe( this.viewLifecycleOwner) { pokemon ->
            // picture download
            binding.apply {
                Glide.with(root)
                    .load(pokemon.image)
                    .apply(RequestOptions().override(300, 300))
                    .error(androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
                    .into(binding.image)
                name.text = pokemon.name?.capitalize()
                height.text = "Height: ${pokemon.height?.toString()} cm"
                weight.text = "Weight: ${pokemon.weight.toString()} kg"
                types.text = "Type: ${pokemon.types}"
            }
        }

        //inflate the layout for this fragment
        return binding.root
    }


}