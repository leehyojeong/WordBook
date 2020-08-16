package com.example.hw03

import android.os.Parcel
import android.os.Parcelable

data class MyWrong (var eng:String,var kor:String) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eng)
        parcel.writeString(kor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyWrong> {
        override fun createFromParcel(parcel: Parcel): MyWrong {
            return MyWrong(parcel)
        }

        override fun newArray(size: Int): Array<MyWrong?> {
            return arrayOfNulls(size)
        }
    }

}