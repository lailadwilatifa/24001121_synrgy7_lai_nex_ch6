package com.example.listingmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.listingmovie.helper.UserDataStoreManager
import com.example.listingmovie.model.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserDataStoreManager) : ViewModel() {

    fun saveProfile(user: User){
        viewModelScope.launch{
            pref.setProfile(user)
        }
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveLogin(isLogged: Boolean){
        viewModelScope.launch {
            pref.setIsLogged(isLogged)
        }
    }

}