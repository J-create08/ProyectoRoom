package com.loguito.clase6.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import com.loguito.clase6.R
import com.loguito.clase6.adapters.MarvelListAdapter
import com.loguito.clase6.db.FavCharacter
import com.loguito.clase6.network.models.Character
import com.loguito.clase6.viewmodels.MarvelListViewModel
import com.loguito.clase6.viewmodels.SaveCharViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_marvel_list.*
import java.util.*
import java.util.concurrent.TimeUnit

class MarvelListFragment : Fragment() {
    private val viewModel: MarvelListViewModel by viewModels()
    private val disposables: CompositeDisposable = CompositeDisposable()
    val DBsaveViewModel: SaveCharViewModel by viewModels()

    private val adapter = MarvelListAdapter { character ->
        findNavController().navigate(R.id.action_marvelListFragment_to_bottomMenuFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel.getCharacterList()
        return inflater.inflate(R.layout.fragment_marvel_list, container, false)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterRecyclerView.adapter = adapter
        characterRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))

//        viewModel.getCharacterListResponse().observe(viewLifecycleOwner) { characterList ->
//            adapter.characterList = characterList
//            characterRecyclerView.visibility = View.VISIBLE
//        }

        viewModel.getCharacterList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { characterList ->
                adapter.characterList = characterList
                characterRecyclerView.visibility = View.VISIBLE
            }

        viewModel.getIsLoading().observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.getIsError().observe(viewLifecycleOwner) { isError ->
            Snackbar.make(parent, R.string.error_text, Snackbar.LENGTH_LONG).show()
        }

        disposables.add(adapter.itemClicked
            .subscribe {character ->
                DBsaveViewModel.insertCharacter(FavCharacter(UUID.randomUUID().toString(), character.name, character.description, character.thumbnail.path))
            })

        disposables.add(searchEditText.textChanges()
            .skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.filter.filter(it)
            })
    }
}