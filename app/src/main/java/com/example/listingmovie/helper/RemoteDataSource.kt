package com.example.listingmovie.helper

import com.example.listingmovie.api.ApiService

class RemoteDataSource (private val apiService: ApiService) {
    suspend fun movieNowPlaying()= apiService.getMovieNowPlaying()
}