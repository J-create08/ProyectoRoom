package com.loguito.clase6.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loguito.clase6.R
import com.loguito.clase6.network.models.Character
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.marvel_character_cell.view.*
import java.util.*

class MarvelListAdapter(val clickListener: (Character) -> Unit) :
    RecyclerView.Adapter<MarvelListAdapter.MarvelCharacterViewHolder>(), Filterable {

    private val clicksAcceptor: PublishSubject<Character> = PublishSubject.create()
    val itemClicked: Observable<Character> = clicksAcceptor.hide()

    var characterList: List<Character> = emptyList()
        set(value) {
            field = value
            filteredCharacters = value
            notifyDataSetChanged()
        }

    private var filteredCharacters: List<Character> = emptyList()

    inner class MarvelCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character) {
            itemView.nameTextView.text = character.name
            itemView.descriptionTextView.text = character.description
            val formattedUrl =
                "${character.thumbnail.path}.${character.thumbnail.extension}".replace(
                    "http",
                    "https"
                )
            Glide.with(itemView.context)
                .load(formattedUrl)
                .circleCrop()
                .into(itemView.characterImageView)
            itemView.setOnClickListener {
                clickListener.invoke(character)
            }

            itemView.setOnClickListener {
                clicksAcceptor.onNext(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.marvel_character_cell, parent, false)
        return MarvelCharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarvelCharacterViewHolder, position: Int) {
        holder.bind(filteredCharacters[position])
    }

    override fun getItemCount() = filteredCharacters.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                filteredCharacters = characterList
                charSequence?.let {
                    if (charSequence.isNotEmpty()) {
                        filteredCharacters = characterList.filter { character ->
                            character.name.toLowerCase(
                                Locale.getDefault()
                            ).contains(charSequence.toString().toLowerCase(Locale.getDefault()))
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredCharacters
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filteredResults: FilterResults?
            ) {
                filteredCharacters = filteredResults?.values as List<Character>
                notifyDataSetChanged()
            }
        }
    }
}