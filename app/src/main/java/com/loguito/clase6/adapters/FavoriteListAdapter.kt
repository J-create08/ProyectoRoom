package com.loguito.clase6.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loguito.clase6.R
import com.loguito.clase6.db.FavCharacter
import kotlinx.android.synthetic.main.favorite_char_cell.view.*

class FavoriteListAdapter(): RecyclerView.Adapter<FavoriteListAdapter.favoriteCharacterViewHolder>() {

    var favoriteList: List<FavCharacter> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class favoriteCharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind (character: FavCharacter) {
            itemView.textName.text = character.name
            itemView.textDescription.text = character.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListAdapter.favoriteCharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_char_cell, parent, false)
        return favoriteCharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: favoriteCharacterViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount() = favoriteList.size

}