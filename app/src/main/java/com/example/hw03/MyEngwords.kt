package com.example.hw03

import android.os.Parcel
import android.os.Parcelable

data class MyEngwords (val engwords:String,var w_count:Int,var fav:Boolean):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(engwords)
        parcel.writeInt(w_count)
        parcel.writeByte(if (fav) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyEngwords> {
        override fun createFromParcel(parcel: Parcel): MyEngwords {
            return MyEngwords(parcel)
        }

        override fun newArray(size: Int): Array<MyEngwords?> {
            return arrayOfNulls(size)
        }
    }
}