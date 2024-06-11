package com.example.listingmovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.UserDataStoreManager
import com.example.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject  constructor(
    private val pref: com.example.data.local.UserDataStoreManager
) : ViewModel() {
    fun saveUser(user: User){
        viewModelScope.launch {
            pref.setRegister(user)
        }
    }
}