package no.jmheiberg.jonma.tictactoe

import android.os.Parcel
import android.os.Parcelable


data class PlayerScore(val name: String, var score: Int, var secondsplayed: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )


    fun getTimePlayed() : String{

        var minutes = 0
        var seconds = 0
        if (secondsplayed > 59) {
            minutes = secondsplayed / 60
            seconds = secondsplayed % 60
        } else {
            seconds = secondsplayed
        }

        return "$minutes min $seconds sec"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(score)
        parcel.writeInt(secondsplayed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerScore> {
        override fun createFromParcel(parcel: Parcel): PlayerScore {
            return PlayerScore(parcel)
        }

        override fun newArray(size: Int): Array<PlayerScore?> {
            return arrayOfNulls(size)
        }
    }
}