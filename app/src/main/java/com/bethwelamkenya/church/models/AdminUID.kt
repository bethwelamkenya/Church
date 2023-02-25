package com.bethwelamkenya.church.models

data class AdminUID(
    val uid: String?,
    val id: Long?,
    val name: String,
    val email: String,
    val number: Long,
    val userName: String,
    val password: String,
    val security: String,
    val answer: String
) : java.io.Serializable
