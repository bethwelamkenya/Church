package com.bethwelamkenya.church.interfaces

import androidx.cardview.widget.CardView
import com.bethwelamkenya.church.models.Admin

interface AdminClicked {
    fun onAdminClicked(admin: Admin)
    fun onAdminLongClicked(admin: Admin, cardView: CardView)
}