package com.example.listingmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.listingmovie.helper.UserDataStoreManager
import com.example.listingmovie.model.User
import kotlinx.coroutines.launch

class LoginViewModel (private val pref:UserDataStoreManager) : ViewModel() {
    fun saveLogin(isLogged: Boolean){
        viewModelScope.launch {
            pref.setIsLogged(isLogged)
        }
    }

    fun getUser(): LiveData<User>{
        return pref.getUser().asLiveData()
    }

    fun getIsLogged(): LiveData<Boolean>{
        return pref.getIsLogged().asLiveData()
    }
}