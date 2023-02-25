package com.bethwelamkenya.church.models

data class MemberUID(
    val uid: String?,
    val id: Long?,
    val name: String,
    val email: String,
    val regNo: String,
    val number: Long,
    val school: String,
    val year: Int,
    val department: String,
    val residence: String
) : java.io.Serializable
