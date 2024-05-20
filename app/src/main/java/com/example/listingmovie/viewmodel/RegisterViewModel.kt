package com.example.listingmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listingmovie.helper.UserDataStoreManager
import com.example.listingmovie.model.User
import kotlinx.coroutines.launch

class RegisterViewModel (private val pref: UserDataStoreManager)  : ViewModel() {
    fun saveUser(user: User){
        viewModelScope.launch {
            pref.setRegister(user)
        }
    }
}