package com.example.listingmovie.domain.model

data class User(
    val username: String,
    val email: String,
    val password: String,
    val namaLengkap: String,
    val alamat: String,
    val tanggalLahir: String
)
