package com.bethwelamkenya.church.models

data class Member(
    val id: Long?,
    val name: String,
    val email: String,
    val number: Long,
    val regNo: String,
    val school: String,
    val year: Int,
    val department: String,
    val residence: String
) : java.io.Serializable
