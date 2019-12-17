package com.vincent.lib.imagecontroller

import android.widget.ImageView

class ImageDisplayParams private constructor(builder: Builder) {
    internal var imageView: ImageView? = null
    internal var imageUrl: String? = null
    internal var width: Int = 0
    internal var height: Int = 0
    internal var errorResourceId: Int = 0
    internal var placeHolderResourceId: Int = 0
    internal var roundValue: Int = 0

    init {
        imageView = builder.imageView
        imageUrl = builder.imageUrl
        width = builder.width
        height = builder.height
        errorResourceId = builder.errorResourceId
        placeHolderResourceId = builder.placeHolderResourceId
        roundValue = builder.roundValue
    }

    class Builder {

        internal var imageView: ImageView? = null
        internal var imageUrl: String? = null
        internal var width: Int = 0
        internal var height: Int = 0
        internal var errorResourceId: Int = 0
        internal var placeHolderResourceId: Int = 0
        internal var roundValue: Int = 0

        fun setImageView(imageView: ImageView): Builder {
            this.imageView = imageView
            return this
        }

        fun setImageUrl(imageUrl: String): Builder {
            this.imageUrl = imageUrl
            return this
        }


        fun setWidth(width: Int): Builder {
            this.width = width
            return this
        }


        fun setHeight(height: Int): Builder {
            this.height = height
            return this
        }


        fun setErrorResource(errorResourceId: Int): Builder {
            this.errorResourceId = errorResourceId
            return this
        }

        fun setRoundValue(roundValue: Int): Builder {
            this.roundValue = roundValue
            return this
        }


        fun setPlaceHolderResourceId(placeHolderResourceId: Int): Builder {
            this.placeHolderResourceId = placeHolderResourceId
            return this
        }

        fun build(): ImageDisplayParams {
            return ImageDisplayParams(this)
        }
    }
}