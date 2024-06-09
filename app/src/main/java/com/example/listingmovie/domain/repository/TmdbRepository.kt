package com.example.listingmovie.domain.repository

import com.example.listingmovie.data.remote.response.MovieResponse

interface TmdbRepository {
    suspend fun getMovieNowPlaying(): MovieResponse
}