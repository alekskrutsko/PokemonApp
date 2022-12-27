package com.example.pokemonapp.ui.list

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
class ListFragment: Fragment(R.layout.fragment_list) {

    var data = ArrayList<Pokemon>()
    private val viewModel: ListViewModel by activityViewModels()
    private var offset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.pokemonRecyclerView.adapter = PokemonListAdapter(data, PokemonListener { pokemon ->
            val direction = pokemon.name?.let {
                ListFragmentDirections.actionPokeListFragmentToPokeDetailsFragment(
                    it
                )
            }
            findNavController().navigate(direction!!)
        })

        val adapter = binding.pokemonRecyclerView.adapter as PokemonListAdapter
        viewModel.pokemons.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                for (element in it){
                    data.add(element)
                    element.id?.let { id -> adapter.notifyItemInserted(id) }
                }
            }else{
                binding.HomeError.visibility = View.VISIBLE
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
                    offset += 30
                    viewModel.getPokemonList(offset, limit)
                    Log.d("offset", offset.toString())
                }
            }
        })
    }

}