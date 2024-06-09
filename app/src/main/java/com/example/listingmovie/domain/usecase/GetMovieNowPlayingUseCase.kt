package com.example.listingmovie.domain.usecase

import com.example.listingmovie.common.Resource
import com.example.listingmovie.data.remote.response.MovieResponse
import com.example.listingmovie.domain.repository.TmdbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieNowPlayingUseCase @Inject constructor(
    private val repository: TmdbRepository
) {
    operator fun invoke(): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading<MovieResponse>())
            val movie = repository.getMovieNowPlaying()
            emit(Resource.Success<MovieResponse>(movie))
        }catch (e: HttpException) {
            emit(Resource.Error<MovieResponse>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException) {
            emit(Resource.Error<MovieResponse>("Couldn't reach server. Check your internet connection."))
        }
    }
}
