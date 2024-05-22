package com.example.listingmovie.repository

import com.example.listingmovie.helper.RemoteDataSource

class MovieRepository (private val remoteDataSource: RemoteDataSource){
    suspend fun movieNowPlaying()=remoteDataSource.movieNowPlaying()
}