package com.lomu.note_mvvm.domain.data

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class NoteDomain(

    val id: Int,
    val title: String?,
    val body: String?,
    val date: Date,
    val categoryId:Int,
    val all: Int = 0

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        TODO("date"),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(body)
        parcel.writeInt(categoryId)
        parcel.writeInt(all)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteDomain> {
        override fun createFromParcel(parcel: Parcel): NoteDomain {
            return NoteDomain(parcel)
        }

        override fun newArray(size: Int): Array<NoteDomain?> {
            return arrayOfNulls(size)
        }
    }
}