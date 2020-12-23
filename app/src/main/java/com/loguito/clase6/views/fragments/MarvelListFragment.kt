package com.loguito.clase6.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.loguito.clase6.R
import com.loguito.clase6.adapters.MarvelListAdapter
import com.loguito.clase6.viewmodels.MarvelListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_marvel_list.*

class MarvelListFragment : Fragment() {
    private val viewModel: MarvelListViewModel by viewModels()
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
    }
}