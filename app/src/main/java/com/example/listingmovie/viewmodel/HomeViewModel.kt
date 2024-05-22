package com.example.listingmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.listingmovie.helper.Resource
import com.example.listingmovie.helper.UserDataStoreManager
import com.example.listingmovie.model.User
import com.example.listingmovie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel (
    private val pref: UserDataStoreManager,
    private val repository: MovieRepository
) : ViewModel()  {
    fun movieNowPlaying() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.movieNowPlaying()))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun getUser(): LiveData<User>{
        return pref.getUser().asLiveData()
    }
}