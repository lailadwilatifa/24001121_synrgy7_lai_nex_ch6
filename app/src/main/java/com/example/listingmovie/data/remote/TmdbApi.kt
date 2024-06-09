package com.example.listingmovie.data.remote

import com.example.listingmovie.common.Constants
import com.example.listingmovie.data.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface TmdbApi {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Header("Authorization") apikey: String = Constants.key
    ): MovieResponse
}