package com.example.data.repository

import com.example.data.remote.TmdbApi
import com.example.domain.model.MovieResponse
import com.example.domain.repository.TmdbRepository
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : TmdbRepository {

    override suspend fun getMovieNowPlaying(): MovieResponse {
        return api.getMovieNowPlaying()
    }
}