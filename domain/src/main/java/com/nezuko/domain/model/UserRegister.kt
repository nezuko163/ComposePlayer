package com.nezuko.domain.model

data class UserRegister(
    val id: String,
    val email: String,
    val password: String,
    val phone: Int = 0,
)
