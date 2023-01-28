package com.example.pokemonapp.presentation.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.data.entities.Pokemon
import com.example.pokemonapp.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

private const val limit = 30

@AndroidEntryPoint
class PokemonListFragment: Fragment(R.layout.fragment_list) {

    private var data = ArrayList<Pokemon>()
    private val viewModel: PokemonListViewModel by activityViewModels()
    private var offset = 0

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.pokemonRecyclerView.adapter = PokemonListAdapter(data, PokemonClickListener { pokemon ->
            val direction = pokemon.name?.let {
                PokemonListFragmentDirections.actionPokeListFragmentToPokeDetailsFragment(
                    it
                )
            }
            findNavController().navigate(direction!!)
        })

        val adapter = binding.pokemonRecyclerView.adapter as PokemonListAdapter
        viewModel.pokemons.observe(viewLifecycleOwner) {
            if (it.isNotEmpty() && data.size != offset+ limit){  //the second part of the condition is needed not to
                // duplicate last portion of data when returning from fragment_detail
                for (element in it){
                    data.add(element)
                    element.id?.let { id -> adapter.notifyItemInserted(id) }
                }
            }else if(data.isEmpty()){
                binding.HomePageInternetError.visibility = View.VISIBLE
            }
        }

        setupScrollListener(binding)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupScrollListener(binding: FragmentListBinding){
        binding.pokemonRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    offset += limit
                    viewModel.getPokemonList(offset, limit)
                    Log.d("offset", offset.toString())
                }
            }
        })
    }

}