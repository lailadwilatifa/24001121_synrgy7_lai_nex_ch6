package com.example.listingmovie.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.listingmovie.helper.UserDataStoreManager

class ViewModelFactory(
    val userDataStoreManager: UserDataStoreManager
) : ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    UserDataStoreManager(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userDataStoreManager) as T
        }else if(modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userDataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}