package com.example.listingmovie.repository

class MovieRepository (private val remoteDataSource: RemoteDataSource){
    suspend fun movieNowPlaying()=remoteDataSource.movieNowPlaying()
}