package com.example.listingmovie.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.listingmovie.api.ApiClient
import com.example.listingmovie.helper.RemoteDataSource
import com.example.listingmovie.helper.UserDataStoreManager
import com.example.listingmovie.repository.MovieRepository

class ViewModelFactory(
    val userDataStoreManager: UserDataStoreManager,
    val remoteDataSource: RemoteDataSource
) : ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    UserDataStoreManager(context),
                    RemoteDataSource(ApiClient.instance)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userDataStoreManager) as T
        }else if(modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userDataStoreManager) as T
        }else if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userDataStoreManager, MovieRepository(remoteDataSource!!)) as T
        }else if(modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userDataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}