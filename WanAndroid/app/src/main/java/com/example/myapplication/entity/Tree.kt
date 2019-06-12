package com.example.myapplication.entity

import android.os.Parcel
import android.os.Parcelable


class Tree() : Parcelable {

    var children: List<Tree>? = null
    var courseId: Int = 0
    var id: Int = 0
    var name: String = ""
    var order: Int = 0
    var parentChapterId: Int = 0
    var userControlSetTop: Boolean? = null
    var visible: Int = 0

    constructor(parcel: Parcel) : this() {
        children = parcel.createTypedArrayList(CREATOR)
        courseId = parcel.readInt()
        id = parcel.readInt()
        name = parcel.readString()
        order = parcel.readInt()
        parentChapterId = parcel.readInt()
        userControlSetTop = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        visible = parcel.readInt()
    }

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