package com.example.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Playlist(
    val playlistId: Int,
    val name: String,
    val artUri: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(playlistId)
        parcel.writeString(name)
        parcel.writeString(artUri)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Playlist> {

        override fun createFromParcel(p0: Parcel): Playlist = Playlist(p0)

        override fun newArray(size: Int): Array<Playlist?> = arrayOfNulls(size)
    }
}