package com.bethwelamkenya.church.models

data class Attendance(
    val attendanceId: Long?,
    val id: Long?,
    val name: String,
    val number: Long?,
    val residence: String?,
    val date: String,
    val status: Int
)
