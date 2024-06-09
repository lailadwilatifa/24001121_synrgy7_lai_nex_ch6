package com.example.listingmovie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.listingmovie.common.Resource
import com.example.listingmovie.data.local.UserDataStoreManager
import com.example.listingmovie.domain.model.User
import com.example.listingmovie.domain.usecase.GetMovieNowPlayingUseCase
import com.example.listingmovie.presentation.state.MovieListState
import com.example.listingmovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pref: UserDataStoreManager,
    private val getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase
) : ViewModel() {

    private val _movieListState = MutableLiveData<MovieListState>()
    val movieListState: LiveData<MovieListState> get() = _movieListState

    init {
        getMovieNowPlaying()
    }

    private fun getMovieNowPlaying() {
        getMovieNowPlayingUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _movieListState.value = MovieListState.Success(result.data?.results ?: emptyList())
                }
                is Resource.Error -> {
                    _movieListState.value = MovieListState.Error(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _movieListState.value = MovieListState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUser(): LiveData<User>{
        return pref.getUser().asLiveData()
    }
}