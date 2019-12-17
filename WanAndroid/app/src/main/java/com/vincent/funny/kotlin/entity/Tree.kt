package com.vincent.funny.kotlin.entity

import android.os.Parcel
import android.os.Parcelable


data class Tree(
    val children: List<Tree>? = null,
    val courseId: Int ,
    val id: Int ,
    val name: String ,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean? = null,
    val visible: Int
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(CREATOR),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(children)
        parcel.writeInt(courseId)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(order)
        parcel.writeInt(parentChapterId)
        parcel.writeValue(userControlSetTop)
        parcel.writeInt(visible)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tree> {
        override fun createFromParcel(parcel: Parcel): Tree {
            return Tree(parcel)
        }

        override fun newArray(size: Int): Array<Tree?> {
            return arrayOfNulls(size)
        }
    }

}