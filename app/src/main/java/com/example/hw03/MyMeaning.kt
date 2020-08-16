package com.example.hw03

import android.os.Parcel
import android.os.Parcelable

data class MyMeaning (val meaning:String,var w_count:Int,var fav:Boolean):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(meaning)
        parcel.writeInt(w_count)
        parcel.writeByte(if (fav) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyMeaning> {
        override fun createFromParcel(parcel: Parcel): MyMeaning {
            return MyMeaning(parcel)
        }

        override fun newArray(size: Int): Array<MyMeaning?> {
            return arrayOfNulls(size)
        }
    }
}