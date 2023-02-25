package com.bethwelamkenya.church.interfaces.admin

import android.widget.CheckBox
import com.bethwelamkenya.church.models.Attendance

interface AttendanceClicked {
    fun checkBoxChanged(attendance: Attendance, isChecked: Boolean, checkBox: CheckBox)
}