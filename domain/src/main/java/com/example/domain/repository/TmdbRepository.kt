package com.example.domain.repository

import com.example.domain.model.MovieResponse


interface TmdbRepository {
    suspend fun getMovieNowPlaying(): MovieResponse
}