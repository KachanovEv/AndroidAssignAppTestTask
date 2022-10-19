package com.testtask.androidassignmentone.data.network.model

import com.google.gson.annotations.SerializedName

data class SlideshowModel(
    @field:SerializedName("order")
    val order: Int,

    @field:SerializedName("template")
    val template: String,

    @field:SerializedName("image")
    val image: Image,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("sentences")
    val sentences: List<Sentence>
)

data class Image(
    @field:SerializedName("assetId")
    val assetId: Int,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("assetTitle")
    val assetTitle: String,

    @field:SerializedName("flipHorizontal")
    val flipHorizontal: Boolean,

    @field:SerializedName("flipVertical")
    val flipVertical: Boolean
)

data class Sentence(
    @field:SerializedName("sentence")
    val sentence: String,

    @field:SerializedName("answer")
    var answer: String,

    var userAnswer: String? = null,

    var correctAnswer: Boolean? = null
)