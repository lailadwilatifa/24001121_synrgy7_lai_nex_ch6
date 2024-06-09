package com.example.listingmovie.data.repository

import com.example.listingmovie.data.remote.TmdbApi
import com.example.listingmovie.data.remote.response.MovieResponse
import com.example.listingmovie.domain.repository.TmdbRepository
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : TmdbRepository {

    override suspend fun getMovieNowPlaying(): MovieResponse {
        return api.getMovieNowPlaying()
    }
}