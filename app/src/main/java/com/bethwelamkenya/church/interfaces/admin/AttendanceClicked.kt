package com.bethwelamkenya.church.interfaces.admin

import android.widget.CheckBox

interface AttendanceClicked {
    fun checkBoxChanged(isChecked: Boolean, checkBox: CheckBox)
}