package com.example.listingmovie.api

import com.example.listingmovie.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Header("Authorization") apikey: String = ApiKey.key
    ): MovieResponse
}