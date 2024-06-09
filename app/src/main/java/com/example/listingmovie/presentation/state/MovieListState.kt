package com.example.listingmovie.presentation.state

import com.example.listingmovie.data.remote.response.Result

sealed interface MovieListState {
    object Loading : MovieListState
    class Success(val movieResult: List<Result>): MovieListState
    class Error(val error: String): MovieListState
}