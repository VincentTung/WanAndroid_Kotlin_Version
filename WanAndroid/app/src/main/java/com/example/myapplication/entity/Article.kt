package com.example.myapplication.entity

import android.os.Parcel
import android.os.Parcelable


data class Article(
    val title: String? = null,
    val type: Int = 0,
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0,
    val apkLink: String? = null,
    val author: String? = null,
    val chapterId: Int = 0,
    val chapterName: String? = null,
    val collect: String? = null,
    val courseId: Int = 0,
    val desc: String? = null,
    val envelopePic: String? = null,
    val fresh: Boolean? = null,
    val id: Int = 0,
    val link: String? = null,
    val niceDate: String? = null,
    val origin: String? = null,
    val prefix: String? = null,
    val projectLink: String? = null,
    val publishTime: Long? = null,
    val superChapterId: Int = 0,
    val superChapterName: String? = null
) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(type)
        parcel.writeInt(userId)
        parcel.writeInt(visible)
        parcel.writeInt(zan)
        parcel.writeString(apkLink)
        parcel.writeString(author)
        parcel.writeInt(chapterId)
        parcel.writeString(chapterName)
        parcel.writeString(collect)
        parcel.writeInt(courseId)
        parcel.writeString(desc)
        parcel.writeString(envelopePic)
        parcel.writeValue(fresh)
        parcel.writeInt(id)
        parcel.writeString(link)
        parcel.writeString(niceDate)
        parcel.writeString(origin)
        parcel.writeString(prefix)
        parcel.writeString(projectLink)
        parcel.writeValue(publishTime)
        parcel.writeInt(superChapterId)
        parcel.writeString(superChapterName)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        if (title != other.title) return false
        if (type != other.type) return false
        if (userId != other.userId) return false
        if (visible != other.visible) return false
        if (zan != other.zan) return false
        if (apkLink != other.apkLink) return false
        if (author != other.author) return false
        if (chapterId != other.chapterId) return false
        if (chapterName != other.chapterName) return false
        if (collect != other.collect) return false
        if (courseId != other.courseId) return false
        if (desc != other.desc) return false
        if (envelopePic != other.envelopePic) return false
        if (fresh != other.fresh) return false
        if (id != other.id) return false
        if (link != other.link) return false
        if (niceDate != other.niceDate) return false
        if (origin != other.origin) return false
        if (prefix != other.prefix) return false
        if (projectLink != other.projectLink) return false
        if (publishTime != other.publishTime) return false
        if (superChapterId != other.superChapterId) return false
        if (superChapterName != other.superChapterName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + type
        result = 31 * result + userId
        result = 31 * result + visible
        result = 31 * result + zan
        result = 31 * result + (apkLink?.hashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + chapterId
        result = 31 * result + (chapterName?.hashCode() ?: 0)
        result = 31 * result + (collect?.hashCode() ?: 0)
        result = 31 * result + courseId
        result = 31 * result + (desc?.hashCode() ?: 0)
        result = 31 * result + (envelopePic?.hashCode() ?: 0)
        result = 31 * result + (fresh?.hashCode() ?: 0)
        result = 31 * result + id
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + (niceDate?.hashCode() ?: 0)
        result = 31 * result + (origin?.hashCode() ?: 0)
        result = 31 * result + (prefix?.hashCode() ?: 0)
        result = 31 * result + (projectLink?.hashCode() ?: 0)
        result = 31 * result + (publishTime?.hashCode() ?: 0)
        result = 31 * result + superChapterId
        result = 31 * result + (superChapterName?.hashCode() ?: 0)
        return result
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }

}