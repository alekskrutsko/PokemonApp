package com.example.pokemonapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.data.entities.Pokemon
import com.example.pokemonapp.databinding.ListViewItemBinding

class PokemonListAdapter(private val pokemonList: List<Pokemon>, private val clickListener: OnClickListener<Pokemon>): RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(var binding: ListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: OnClickListener<Pokemon>, pokemon : Pokemon) {
            binding.pokemon = pokemon
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(
            ListViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder : PokemonViewHolder, position : Int) {
        val pokemon = pokemonList[position]
        holder.bind(clickListener, pokemon)
    }


    override fun getItemCount(): Int =  pokemonList.size
}