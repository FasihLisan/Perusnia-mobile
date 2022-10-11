package com.example.perusnia

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val GambarBuku: Int,
    val JudulBuku: String,
    val Pengarang : String,
    val Harga: String,
    val TglUpload: String,
    val Rate: String,
    val Read: Int,
    val Page: Int,
    val Sinopsis: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(GambarBuku)
        parcel.writeString(JudulBuku)
        parcel.writeString(Pengarang)
        parcel.writeString(Harga)
        parcel.writeString(TglUpload)
        parcel.writeString(Rate)
        parcel.writeInt(Read)
        parcel.writeInt(Page)
        parcel.writeString(Sinopsis)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}
