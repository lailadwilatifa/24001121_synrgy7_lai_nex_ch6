package com.example.data.remote

import com.example.common.Constants
import com.example.domain.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface TmdbApi {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Header("Authorization") apikey: String = Constants.key
    ): MovieResponse
}