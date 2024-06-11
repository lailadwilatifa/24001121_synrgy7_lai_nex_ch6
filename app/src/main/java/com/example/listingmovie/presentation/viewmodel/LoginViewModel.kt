package com.example.listingmovie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.local.UserDataStoreManager
import com.example.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val pref: com.example.data.local.UserDataStoreManager
) : ViewModel() {
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