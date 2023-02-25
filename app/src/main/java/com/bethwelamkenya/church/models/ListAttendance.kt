package com.bethwelamkenya.church.models

data class ListAttendance(
    val attendanceId: Long?,
    val id: Long?,
    val name: String,
    val date: String,
    val status: Int
) : java.io.Serializable
