package com.bethwelamkenya.church.interfaces

import androidx.cardview.widget.CardView
import com.bethwelamkenya.church.models.Member

interface MemberClicked {
    fun onMemberClicked(member: Member)
    fun onMemberLongClicked(member: Member, cardView: CardView)
}