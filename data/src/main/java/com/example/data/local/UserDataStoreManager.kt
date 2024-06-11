package com.example.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager (private val context: Context) {
    suspend fun setRegister(user: User) {
        context.userDataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
        }
    }

    suspend fun setProfile(user: User){
        context.userDataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[NAMA_LENGKAP_KEY] = user.namaLengkap
            preferences[ALAMAT_KEY] = user.alamat
            preferences[TANGGAL_LAHIR_KEY] = user.tanggalLahir
            preferences[FOTO_KEY] = user.foto

        }
    }

    fun getUser(): Flow<User> {
        return context.userDataStore.data.map { preferences ->
            User(
                preferences[USERNAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
                preferences[NAMA_LENGKAP_KEY] ?: "",
                preferences[TANGGAL_LAHIR_KEY] ?: "",
                preferences[ALAMAT_KEY] ?: "",
                preferences[FOTO_KEY] ?: "",
            )
        }
    }

    suspend fun setIsLogged(isLogged: Boolean){
        context.userDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLogged
        }
    }

    fun getIsLogged(): Flow<Boolean>{
        return context.userDataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }
    }

    companion object {
        private const val DATASTORE_NAME = "user_preferences"

        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val EMAIL_KEY = stringPreferencesKey("email_key")
        private val PASSWORD_KEY = stringPreferencesKey("password_key")
        private val NAMA_LENGKAP_KEY = stringPreferencesKey("nama_lengkap_key")
        private val TANGGAL_LAHIR_KEY = stringPreferencesKey("tanggal_lahir_key")
        private val ALAMAT_KEY = stringPreferencesKey("alamat_key")
        private val FOTO_KEY = stringPreferencesKey("foto_key")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in_key")

        private val Context.userDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}