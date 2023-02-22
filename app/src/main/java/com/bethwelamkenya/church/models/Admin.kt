package com.bethwelamkenya.church.models

data class Admin(
    val id: Long?,
    val name: String,
    val email: String,
    val number: String,
    val userName: String,
    val password: String,
    val security: String,
    val answer: String
) : java.io.Serializable
