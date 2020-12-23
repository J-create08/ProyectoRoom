package com.loguito.clase6.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loguito.clase6.network.RetrofitProvider
import com.loguito.clase6.network.models.Character
import io.reactivex.rxjava3.core.Observable

class MarvelListViewModel : ViewModel() {
    private val retrofitProvider = RetrofitProvider()
    private val apiKey: String = "001ac6c73378bbfff488a36141458af2"
    private val hash: String = "72e5ed53d1398abb831c3ceec263f18b"
    private val timestamp: String = "thesoer"

    //    private val characterListResponse: MutableLiveData<List<Character>> = MutableLiveData()
    private val isMakingRequest: MutableLiveData<Boolean> = MutableLiveData()
    private val isError: MutableLiveData<Boolean> = MutableLiveData()

//    fun getCharacterListResponse() : LiveData<List<Character>> {
//        return characterListResponse
//    }

    fun getIsLoading(): LiveData<Boolean> {
        return isMakingRequest
    }

    fun getIsError(): LiveData<Boolean> {
        return isError
    }

    fun getCharacterList(): Observable<List<Character>> {
        isMakingRequest.postValue(true)
        return retrofitProvider.getMarvelService().getCharacterList(apiKey, hash, timestamp)
            .map { response -> response.data.results }
//            .switchMap { list ->
//                Observable.fromIterable(list)
//                    .filter { character -> character.name.contains("Man") }
//                    .toList()
//                    .toObservable()
//            }
            .doOnError { isError.postValue(true) }
            .onErrorReturn { emptyList() }
            .flatMapIterable { list -> list }
            .flatMap { item ->
                retrofitProvider.getMarvelService()
                    .getCharacterDetail(item.id, apiKey, hash, timestamp)
                    .map { detailResponse -> detailResponse.data.results[0] }
            }
            .toList()
            .toObservable()
            .doOnNext { isMakingRequest.postValue(false) }
    }
}